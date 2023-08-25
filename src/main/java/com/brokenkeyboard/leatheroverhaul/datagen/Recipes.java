package com.brokenkeyboard.leatheroverhaul.datagen;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import com.brokenkeyboard.leatheroverhaul.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.leatheroverhaul.datagen.conditions.LeatherBundleCondition;
import com.brokenkeyboard.leatheroverhaul.datagen.recipe.ArmorkitUpgrade;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider implements IConditionBuilder {

    public Recipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        armorUpgrade(consumer, LeatherOverhaul.ARMOR_KIT.get(), LeatherOverhaul.LEATHER_HELMET.get());
        armorUpgrade(consumer, LeatherOverhaul.ARMOR_KIT.get(), LeatherOverhaul.LEATHER_CHESTPLATE.get());
        armorUpgrade(consumer, LeatherOverhaul.ARMOR_KIT.get(), LeatherOverhaul.LEATHER_LEGGINGS.get());
        armorUpgrade(consumer, LeatherOverhaul.ARMOR_KIT.get(), LeatherOverhaul.LEATHER_BOOTS.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, LeatherOverhaul.ARMOR_KIT.get())
                .define('L', Tags.Items.LEATHER)
                .define('S', Tags.Items.STRING)
                .pattern("SL")
                .pattern("LS")
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(consumer);

        ConditionalRecipe.builder().addCondition(new HideBundleCondition())
                .addRecipe(
                        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, LeatherOverhaul.BUNDLE.get())
                                .define('S', Tags.Items.STRING)
                                .define('H', Items.RABBIT_HIDE)
                                .pattern("SHS")
                                .pattern("H H")
                                .pattern("HHH")
                                .group("bundles")
                                .unlockedBy("has_string", has(Tags.Items.STRING))
                                .unlockedBy("has_rabbit_hide", has(Items.RABBIT_HIDE))
                                ::save
                ).generateAdvancement().build(consumer, new ResourceLocation(LeatherOverhaul.MOD_ID, LeatherOverhaul.BUNDLE.get() + "_hide"));

        ConditionalRecipe.builder().addCondition(new LeatherBundleCondition())
                .addRecipe(
                        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, LeatherOverhaul.BUNDLE.get())
                                .define('S', Tags.Items.STRING)
                                .define('L', Tags.Items.LEATHER)
                                .pattern("SLS")
                                .pattern("L L")
                                .pattern("LLL")
                                .group("bundles")
                                .unlockedBy("has_string", has(Tags.Items.STRING))
                                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                                ::save
                ).generateAdvancement().build(consumer, new ResourceLocation(LeatherOverhaul.MOD_ID, LeatherOverhaul.BUNDLE.get() + "_leather"));

        SpecialRecipeBuilder.special(LeatherOverhaul.SCRAP_LEATHER.get())
                .save(consumer, "scrap_leather");

        SpecialRecipeBuilder.special(LeatherOverhaul.POTION_ARMORKIT.get())
                .save(consumer, "potion_armorkit");
    }

    public static SmithingTransformRecipeBuilder upgradeRecipe(Ingredient template, Ingredient ingredient, Ingredient addition, Item result) {
        return new SmithingTransformRecipeBuilder(ArmorkitUpgrade.Serializer.INSTANCE, template, ingredient, addition, RecipeCategory.COMBAT, result);
    }

    protected static void armorUpgrade(Consumer<FinishedRecipe> consumer, Item template, Item armor) {
        upgradeRecipe(Ingredient.of(template), Ingredient.of(armor), Ingredient.of(ItemStack.EMPTY), armor)
                .unlocks("has_" + getItemName(armor), has(armor))
                .save(consumer, getItemName(armor) + "_smithing");
    }
}