package com.brokenkeyboard.betterleather.datagen;

import com.brokenkeyboard.betterleather.BetterLeather;
import com.brokenkeyboard.betterleather.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.betterleather.datagen.conditions.LeatherBundleCondition;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(BetterLeather.ARMOR_KIT.get())
                .define('L', Items.LEATHER)
                .define('S', Items.STRING)
                .pattern("SL")
                .pattern("LS")
                .unlockedBy("has_string", has(Items.STRING))
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer);

        ConditionalRecipe.builder().addCondition(new HideBundleCondition())
                .addRecipe(
                        ShapedRecipeBuilder.shaped(BetterLeather.BUNDLE.get())
                                .define('S', Tags.Items.STRING)
                                .define('H', Items.RABBIT_HIDE)
                                .pattern("SHS")
                                .pattern("H H")
                                .pattern("HHH")
                                .group("bundles")
                                .unlockedBy("has_string", has(Tags.Items.STRING))
                                .unlockedBy("has_rabbit_hide", has(Items.RABBIT_HIDE))
                                ::save
                ).generateAdvancement().build(consumer, new ResourceLocation(BetterLeather.MOD_ID, BetterLeather.BUNDLE.get() + "_hide"));

        ConditionalRecipe.builder().addCondition(new LeatherBundleCondition())
                .addRecipe(
                        ShapedRecipeBuilder.shaped(BetterLeather.BUNDLE.get())
                                .define('S', Tags.Items.STRING)
                                .define('L', Tags.Items.LEATHER)
                                .pattern("SLS")
                                .pattern("L L")
                                .pattern("LLL")
                                .group("bundles")
                                .unlockedBy("has_string", has(Tags.Items.STRING))
                                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                                ::save
                ).generateAdvancement().build(consumer, new ResourceLocation(BetterLeather.MOD_ID, BetterLeather.BUNDLE.get() + "_leather"));

        SpecialRecipeBuilder.special((SimpleRecipeSerializer<?>) BetterLeather.SCRAP_LEATHER.get())
                .save(consumer, "scrap_leather");
    }
}