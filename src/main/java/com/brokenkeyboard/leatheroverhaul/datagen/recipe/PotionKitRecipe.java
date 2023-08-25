package com.brokenkeyboard.leatheroverhaul.datagen.recipe;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import com.google.common.collect.Lists;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.getPotionEffect;
import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.setPotionEffect;

public class PotionKitRecipe extends CustomRecipe {
    public PotionKitRecipe(ResourceLocation location, CraftingBookCategory category) {
        super(location, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<ItemStack> list = Lists.newArrayList();
        return validRecipe(list, container);
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        List<ItemStack> list = Lists.newArrayList();
        ItemStack result = new ItemStack(LeatherOverhaul.ARMOR_KIT.get());
        if (validRecipe(list, container)) {
            for (ItemStack i : list) {
                if(i.getItem().equals(Items.POTION)) {
                    MobEffectInstance effect = PotionUtils.getMobEffects(i).get(0);
                    setPotionEffect(result, effect);
                } else if (i.getItem().equals(LeatherOverhaul.ARMOR_KIT.get())) {
                    result.setDamageValue(i.getDamageValue());
                }
            }
        }
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LeatherOverhaul.POTION_ARMORKIT.get();
    }

    private boolean validRecipe(List<ItemStack> list, CraftingContainer container) {
        boolean hasPotion = false;
        boolean hasKit = false;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (itemstack.getItem().equals(Items.POTION) && PotionUtils.getMobEffects(itemstack).size() == 1) {
                MobEffectInstance effect = PotionUtils.getMobEffects(itemstack).get(0);
                if(effect.getEffect().isBeneficial() && !effect.getEffect().isInstantenous()) {
                    list.add(itemstack);
                    hasPotion = true;
                }
            } else if (itemstack.getItem().equals(LeatherOverhaul.ARMOR_KIT.get()) && getPotionEffect(itemstack) == null) {
                list.add(itemstack);
                hasKit = true;
            }
        }
        return list.size() == 2 && hasPotion && hasKit;
    }
}