package com.brokenkeyboard.leatheroverhaul.mixin;

import com.brokenkeyboard.leatheroverhaul.RenderDurability;
import com.brokenkeyboard.leatheroverhaul.item.LeatherArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Unique
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(at = @At("HEAD"), method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V")
    public void renderGuiItemDecorations(Font font, ItemStack stack, int xPos, int yPos, @Nullable String pText, CallbackInfo callbackInfo) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof LeatherArmor && stack.getTag() != null && stack.getTag().contains("bonus_armor")) {
                PoseStack poseStack = new PoseStack();
                poseStack.translate(xPos, yPos, ((ItemRenderer) (Object) this).blitOffset);
                RenderDurability.render(stack, xPos, yPos);
            }
        }
    }
}