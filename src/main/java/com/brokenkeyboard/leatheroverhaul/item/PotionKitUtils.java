package com.brokenkeyboard.leatheroverhaul.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PotionKitUtils {

    public static void displayPotionEffect(ItemStack stack, List<Component> components) {
        MobEffectInstance effect = getPotionEffect(stack);
        MutableComponent mutablecomponent = new TranslatableComponent(effect.getDescriptionId());
        if (effect.getAmplifier() > 0) {
            mutablecomponent = new TranslatableComponent("potion.withAmplifier", mutablecomponent, new TranslatableComponent("potion.potency." + effect.getAmplifier()));
        }

        if (effect.getDuration() > 20) {
            mutablecomponent = new TranslatableComponent("potion.withDuration", mutablecomponent, formatKitDuration(effect, 1.0F));
        }
        components.add(mutablecomponent.withStyle(effect.getEffect().getCategory().getTooltipFormatting()));
    }

    public static void setPotionEffect(ItemStack stack, MobEffectInstance effect) {
        CompoundTag potionTag = new CompoundTag();
        effect.save(potionTag);
        stack.addTagElement("potion_effect", potionTag);
    }

    public static MobEffectInstance getPotionEffect(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement("potion_effect");
        return MobEffectInstance.load(tag);
    }

    public static int getKitDuration(MobEffectInstance effectInstance) {
        MobEffect effect = effectInstance.getEffect();

        if (effect.equals(MobEffects.REGENERATION))
            return (effectInstance.getAmplifier() > 0 ? 60 : (effectInstance.getDuration() / 9));

        if (effect.equals(MobEffects.SLOW_FALLING))
            return (effectInstance.getDuration() / 30 + 40);

        return (effectInstance.getDuration() / 60) + (effectInstance.getAmplifier() > 0 ? 30 : 40);
    }

    public static String formatKitDuration(MobEffectInstance effect, float value) {
        if (effect.isNoCounter()) {
            return "**:**";
        } else {
            int i = Mth.floor((float)getKitDuration(effect) * value);
            return StringUtil.formatTickDuration(i);
        }
    }
}