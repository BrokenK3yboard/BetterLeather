package com.brokenkeyboard.betterleather.datagen;

import com.brokenkeyboard.betterleather.BetterLeather;
import com.brokenkeyboard.betterleather.item.LeatherArmor;
import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.util.List;

public class LeatherRecipe extends CustomRecipe {
    public LeatherRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<ItemStack> list = Lists.newArrayList();
        return validRecipe(list, container);
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        List<ItemStack> list = Lists.newArrayList();
        if (validRecipe(list, container)) {

            ItemStack leather = ItemStack.EMPTY;

            for (ItemStack i : list) {
                if(i.getItem() instanceof LeatherArmor || i.getItem().equals(Items.SADDLE) || i.getItem().equals(Items.LEATHER_HORSE_ARMOR)) {
                    leather = i;
                }
            }

            if(leather.getItem() instanceof LeatherArmor) {
                int damagePercent = 100 - (int)((double)(leather.getDamageValue()) / (double)leather.getMaxDamage() * 100);
                System.out.println(damagePercent);
                return new ItemStack(Items.LEATHER, damagePercent >= 50 ? 2 : 1);
            } else {
                return new ItemStack(Items.LEATHER, 3);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BetterLeather.SCRAP_LEATHER.get();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (itemstack.hasContainerItem()) {
                nonnulllist.set(i, itemstack.getContainerItem());
            } else if (itemstack.is(Tags.Items.SHEARS)) {
                ItemStack shears = itemstack.copy();
                shears.setDamageValue(itemstack.getDamageValue() + 3);

                if(shears.getDamageValue() > shears.getMaxDamage())
                    shears = ItemStack.EMPTY;

                nonnulllist.set(i, shears);
                break;
            }
        }
        return nonnulllist;
    }

    private boolean validRecipe(List<ItemStack> list, CraftingContainer container) {
        boolean hasShears = false;
        boolean hasLeather = false;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (itemstack.is(Tags.Items.SHEARS)) {
                list.add(itemstack);
                hasShears = true;
            } else if (itemstack.getItem() instanceof LeatherArmor || itemstack.getItem().equals(Items.SADDLE) || itemstack.getItem().equals(Items.LEATHER_HORSE_ARMOR)) {
                list.add(itemstack);
                hasLeather = true;
            }
        }
        return list.size() == 2 && hasShears && hasLeather;
    }
}