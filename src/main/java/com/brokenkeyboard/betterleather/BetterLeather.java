package com.brokenkeyboard.betterleather;

import com.brokenkeyboard.betterleather.datagen.LeatherRecipe;
import com.brokenkeyboard.betterleather.datagen.UpgradeRecipe;
import com.brokenkeyboard.betterleather.datagen.loot.LeatherDrops.LeatherDropsSerializer;
import com.brokenkeyboard.betterleather.item.ArmorKitItem;
import com.brokenkeyboard.betterleather.item.LeatherArmor;
import com.brokenkeyboard.betterleather.item.LeatherBundle;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(BetterLeather.MOD_ID)
public class BetterLeather
{
    public static final String MOD_ID = "betterleather";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS_OVERRIDE = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final RegistryObject<Item> ARMOR_KIT = ITEMS.register("armor_kit", () -> new ArmorKitItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));
    public static final RegistryObject<Item> LEATHER_HELMET = ITEMS_OVERRIDE.register("leather_helmet", () -> new LeatherArmor(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> LEATHER_CHESTPLATE = ITEMS_OVERRIDE.register("leather_chestplate", () -> new LeatherArmor(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> LEATHER_LEGGINGS = ITEMS_OVERRIDE.register("leather_leggings", () -> new LeatherArmor(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> LEATHER_BOOTS = ITEMS_OVERRIDE.register("leather_boots", () -> new LeatherArmor(ArmorMaterials.LEATHER, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BUNDLE = ITEMS_OVERRIDE.register("bundle", () -> new LeatherBundle(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<RecipeSerializer<UpgradeRecipe>> KIT_UPGRADE = RECIPES.register("kit_upgrade", () -> UpgradeRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<LeatherRecipe>> SCRAP_LEATHER = RECIPES.register("scrap_leather", () -> new SimpleRecipeSerializer<>(LeatherRecipe::new));

    public static final RegistryObject<LeatherDropsSerializer> LEATHER_COW = GLM.register("leather_cow", LeatherDropsSerializer::new);

    public static int leatherDrops;
    public static boolean hideBundle;
    public static boolean leatherBundle;
    public static double kitBase;
    public static double kitBonus;
    public static double kitRepair;

    public BetterLeather() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Config.registerConfig();
        ITEMS.register(bus);
        ITEMS_OVERRIDE.register(bus);
        RECIPES.register(bus);
        GLM.register(bus);
    }
}