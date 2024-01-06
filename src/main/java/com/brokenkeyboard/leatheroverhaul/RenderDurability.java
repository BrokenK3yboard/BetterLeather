package com.brokenkeyboard.leatheroverhaul;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

import static com.brokenkeyboard.leatheroverhaul.item.LeatherArmor.getBonusArmor;
import static com.brokenkeyboard.leatheroverhaul.item.LeatherArmor.getBonusArmorMax;

public class RenderDurability implements IItemDecorator {

    public static final RenderDurability INSTANCE = new RenderDurability();

    @Override
    public boolean render(Font font, ItemStack stack, int xOffset, int yOffset, float blitOffset) {
        if (stack.getTag() != null && stack.getTag().contains("bonus_armor")) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableBlend();
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();

            int barWidth = Math.min(1 + 12 * getBonusArmor(stack) / getBonusArmorMax(stack), 13);
            int offset = stack.isDamaged() ? 0 : 2;
            fillRect(buffer, xOffset + 2, yOffset + 11 + offset, 13, 2, 0, 0, 0);
            fillRect(buffer,xOffset + 2, yOffset + 11 + offset, barWidth, 1, 102, 102, 255);

            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
        }
        return false;
    }

    private static void fillRect(BufferBuilder buffer, int pX, int pY, int width, int height, int red, int green, int blue) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(pX, pY, 0.0D).color(red, green, blue, 255).endVertex();
        buffer.vertex(pX, pY + height, 0.0D).color(red, green, blue, 255).endVertex();
        buffer.vertex(pX + width, pY + height, 0.0D).color(red, green, blue, 255).endVertex();
        buffer.vertex(pX + width, pY, 0.0D).color(red, green, blue, 255).endVertex();
        BufferUploader.drawWithShader(buffer.end());
    }
}