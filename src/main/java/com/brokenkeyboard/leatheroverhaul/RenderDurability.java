package com.brokenkeyboard.leatheroverhaul;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

import static com.brokenkeyboard.leatheroverhaul.item.LeatherArmor.getBonusArmor;
import static com.brokenkeyboard.leatheroverhaul.item.LeatherArmor.getBonusArmorMax;

public class RenderDurability implements IItemDecorator {

    public static final RenderDurability INSTANCE = new RenderDurability();
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (stack.getTag() != null && stack.getTag().contains("bonus_armor")) {
            int barWidth = Math.min(1 + 12 * getBonusArmor(stack) / getBonusArmorMax(stack), 13);
            int offset = stack.isDamaged() ? 0 : 2;
            int xPos = xOffset + 2;
            int yPos = yOffset + 11;
            guiGraphics.fill(RenderType.guiOverlay(), xPos, yPos + offset, xPos + 13, yPos + 2 + offset, -16777216);
            guiGraphics.fill(RenderType.guiOverlay(), xPos, yPos + offset, xPos + barWidth, yPos + 1 + offset, BAR_COLOR | -16777216);
        }
        return false;
    }
}
