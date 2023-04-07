package com.brokenkeyboard.betterleather.datagen;

import com.brokenkeyboard.betterleather.BetterLeather;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class Smithing extends RecipeProvider {

    public Smithing(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        armorUpgrade(consumer, BetterLeather.LEATHER_HELMET.get(), BetterLeather.ARMOR_KIT.get());
        armorUpgrade(consumer, BetterLeather.LEATHER_CHESTPLATE.get(), BetterLeather.ARMOR_KIT.get());
        armorUpgrade(consumer, BetterLeather.LEATHER_LEGGINGS.get(), BetterLeather.ARMOR_KIT.get());
        armorUpgrade(consumer, BetterLeather.LEATHER_BOOTS.get(), BetterLeather.ARMOR_KIT.get());
    }

    public static UpgradeRecipeBuilder upgradeRecipe(Ingredient input, Ingredient ingredient, Item result) {
        return new UpgradeRecipeBuilder(UpgradeRecipe.Serializer.INSTANCE, input, ingredient, result);
    }

    protected static void armorUpgrade(Consumer<FinishedRecipe> consumer, Item input, Item ingredient) {
        upgradeRecipe(Ingredient.of(input), Ingredient.of(ingredient), input)
                .unlocks("has_" + getItemName(ingredient), has(ingredient))
                .save(consumer, getItemName(input) + "_smithing");
    }
}