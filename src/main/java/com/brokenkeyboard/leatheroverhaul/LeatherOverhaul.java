package com.brokenkeyboard.leatheroverhaul;

import com.brokenkeyboard.leatheroverhaul.datagen.loot.LeatherDrops.LeatherDropsSerializer;
import com.brokenkeyboard.leatheroverhaul.datagen.recipe.ArmorkitUpgrade;
import com.brokenkeyboard.leatheroverhaul.datagen.recipe.LeatherRecipe;
import com.brokenkeyboard.leatheroverhaul.datagen.recipe.PotionKitRecipe;
import com.brokenkeyboard.leatheroverhaul.item.ArmorKitItem;
import com.brokenkeyboard.leatheroverhaul.item.LeatherArmor;
import com.brokenkeyboard.leatheroverhaul.item.LeatherBundle;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
@Mod(LeatherOverhaul.MOD_ID)
public class LeatherOverhaul
{
    public static final String MOD_ID = "leatheroverhaul";
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

    public static final RegistryObject<RecipeSerializer<ArmorkitUpgrade>> KIT_UPGRADE = RECIPES.register("kit_upgrade", () -> ArmorkitUpgrade.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<LeatherRecipe>> SCRAP_LEATHER = RECIPES.register("scrap_leather", () -> new SimpleRecipeSerializer<>(LeatherRecipe::new));
    public static final RegistryObject<RecipeSerializer<PotionKitRecipe>> POTION_ARMORKIT = RECIPES.register("potion_armorkit", () -> new SimpleRecipeSerializer<>(PotionKitRecipe::new));

    public static final RegistryObject<LeatherDropsSerializer> LEATHER_COW = GLM.register("leather_cow", LeatherDropsSerializer::new);

    public LeatherOverhaul() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Config.registerConfig();
        ITEMS.register(bus);
        ITEMS_OVERRIDE.register(bus);
        RECIPES.register(bus);
        GLM.register(bus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_HELMET.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_BOOTS.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(LeatherOverhaul.BUNDLE.get(), CauldronInteraction.DYED_ITEM);
    }
}