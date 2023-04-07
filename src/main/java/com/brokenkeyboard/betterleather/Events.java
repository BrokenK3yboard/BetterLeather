package com.brokenkeyboard.betterleather;

import com.brokenkeyboard.betterleather.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.betterleather.datagen.conditions.LeatherBundleCondition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class Events {

    @Mod.EventBusSubscriber(modid = BetterLeather.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void armorColor(ColorHandlerEvent.Item event) {
            CauldronInteraction.WATER.put(BetterLeather.LEATHER_HELMET.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(BetterLeather.LEATHER_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(BetterLeather.LEATHER_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(BetterLeather.LEATHER_BOOTS.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(BetterLeather.BUNDLE.get(), CauldronInteraction.DYED_ITEM);
        }

        @SubscribeEvent
        public static void registerItemColor(ColorHandlerEvent.Item event) {
            event.getItemColors().register((stack, value) -> value > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), BetterLeather.BUNDLE.get());
        }

        @SubscribeEvent
        public static void registerSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            if (!(event.getRegistry().getRegistryKey() == ForgeRegistries.Keys.RECIPE_SERIALIZERS)) return;
            CraftingHelper.register(HideBundleCondition.SERIALIZER);
            CraftingHelper.register(LeatherBundleCondition.SERIALIZER);
        }

        @SubscribeEvent
        public static void configLoaded(ModConfigEvent.Loading event) {
            configUpdate();
        }

        @SubscribeEvent
        public static void configReloaded(ModConfigEvent.Reloading event) {
            configUpdate();
        }
    }

    private static void configUpdate() {
        BetterLeather.leatherDrops = Config.LEATHER_DROPS.get();
        BetterLeather.kitBase = Config.KIT_BASE.get();
        BetterLeather.kitBonus = Config.KIT_BONUS.get();
        BetterLeather.kitRepair = Config.KIT_REPAIR.get();
        BetterLeather.hideBundle = Config.BUNDLE_CRAFT_HIDE.get();
        BetterLeather.leatherBundle = Config.BUNDLE_CRAFT_LEATHER.get();
    }
}