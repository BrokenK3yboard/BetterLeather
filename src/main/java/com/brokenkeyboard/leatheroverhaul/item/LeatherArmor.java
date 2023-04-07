package com.brokenkeyboard.leatheroverhaul.item;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.Consumer;

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
            entity.getLevel().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_BREAK, entity.getSoundSource(),
                    0.8F, 0.8F + entity.getLevel().random.nextFloat() * 0.4F, false);
        }
        return amount;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if(getBonusArmor(stack) > 0)
            return true;
        return super.isBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if(getBonusArmor(stack) > 0) {
            return Math.min(1 + 12 * getBonusArmor(stack) / getBonusArmorMax(stack), 13);
        }
        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if(getBonusArmor(stack) > 0)
            return BAR_COLOR;
        return super.getBarColor(stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (type == null || !type.equals("overlay") || LeatherArmor.getBonusArmor(stack) <= 0) return null;
        return LeatherOverhaul.MOD_ID + ":textures/models/armor/leather_armored_layer_" + (slot.equals(EquipmentSlot.LEGS) ? 2 : 1) + "_overlay.png";
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