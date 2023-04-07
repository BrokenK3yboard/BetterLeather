package com.brokenkeyboard.betterleather.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class LeatherBundle extends BundleItem implements DyeableLeatherItem {
    public LeatherBundle(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 13659447;
    }
}