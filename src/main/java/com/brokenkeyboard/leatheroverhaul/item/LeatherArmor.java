package com.brokenkeyboard.leatheroverhaul.item;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.displayPotionEffect;
import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.getPotionEffect;

public class LeatherArmor extends DyeableArmorItem {

    private static final UUID[] ARMOR_UUID = getUUID();
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public LeatherArmor(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (getBonusArmor(stack) <= 0) return amount;

        if (amount < getBonusArmor(stack)) {
            setBonusArmor(stack, getBonusArmor(stack) - amount);
            amount = 0;
        } else {
            amount = amount - getBonusArmor(stack);
            setBonusArmor(stack,0);
            stack.removeTagKey("potion_effect");
        }

        MobEffectInstance effectInstance = getPotionEffect(stack);

        if (getBonusArmor(stack) > 0 && effectInstance != null) {
            MobEffect effect = effectInstance.getEffect();
            int duration = PotionKitUtils.getKitDuration(effectInstance);
            int maxDuration = effect.equals(MobEffects.REGENERATION) ? (effectInstance.getDuration() * 2 / 5) : (effectInstance.getDuration() / 10);
            int amplifier = effectInstance.getAmplifier();

            MobEffectInstance currentEffect = entity.getEffect(effect);

            if (currentEffect != null && currentEffect.getDuration() < maxDuration && currentEffect.getAmplifier() == amplifier) {
                MobEffectInstance newEffect = new MobEffectInstance(effect, Math.min(currentEffect.getDuration() + duration, maxDuration), amplifier);
                entity.addEffect(newEffect);
            } else {
                entity.addEffect(new MobEffectInstance(effect, Math.min(duration, maxDuration), amplifier));
            }
        }
        return amount;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (getPotionEffect(stack) != null)
            displayPotionEffect(stack, components);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (getBonusArmor(stack) > 0)
            return true;
        return super.isBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (getBonusArmor(stack) > 0) {
            return Math.min(1 + 12 * getBonusArmor(stack) / getBonusArmorMax(stack), 13);
        }
        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (getBonusArmor(stack) > 0)
            return BAR_COLOR;
        return super.getBarColor(stack);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, @Nullable String type) {
        if (LeatherArmor.getBonusArmor(stack) <= 0) return null;
        if (type == null) {
            return LeatherOverhaul.MOD_ID + ":textures/models/armor/leather_armored_layer_" + (slot.equals(EquipmentSlot.LEGS) ? 2 : 1) + ".png";
        } else {
            return LeatherOverhaul.MOD_ID + ":textures/models/armor/leather_armored_layer_" + (slot.equals(EquipmentSlot.LEGS) ? 2 : 1) + "_overlay.png";
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (!(slot == this.getSlot())) return super.getAttributeModifiers(slot, stack);

        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        double value = this.getDefense() + (LeatherArmor.getBonusArmor(stack) > 0 ? 1 : 0);
        multimap.put(Attributes.ARMOR, new AttributeModifier(ARMOR_UUID[slot.getIndex()], "Armor modifier", value, AttributeModifier.Operation.ADDITION));
        return multimap;
    }

    public static void setBonusArmorMax(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt("max_bonus_armor", amount);
    }

    public static int getBonusArmorMax(ItemStack stack) {
        return stack.getOrCreateTag().getInt("max_bonus_armor");
    }

    public static void setBonusArmor(ItemStack stack, int bonus) {
        stack.getOrCreateTag().putInt("bonus_armor", bonus);
    }

    public static int getBonusArmor(ItemStack stack) {
        return stack.getOrCreateTag().getInt("bonus_armor");
    }

    private static UUID[] getUUID() {
        UUID[] ARMOR_UUID = new UUID[0];
        try {
            Field field = ObfuscationReflectionHelper.findField(ArmorItem.class, "f_40380_");
            ARMOR_UUID = (UUID[]) field.get(ARMOR_UUID);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        return ARMOR_UUID;
    }
}