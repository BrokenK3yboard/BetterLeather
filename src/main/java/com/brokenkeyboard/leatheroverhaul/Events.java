package com.brokenkeyboard.leatheroverhaul;

import com.brokenkeyboard.leatheroverhaul.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.leatheroverhaul.datagen.conditions.LeatherBundleCondition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class Events {

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void registerColorEvents(RegisterColorHandlersEvent.Item event) {
            CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_HELMET.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(LeatherOverhaul.LEATHER_BOOTS.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.put(LeatherOverhaul.BUNDLE.get(), CauldronInteraction.DYED_ITEM);
            event.register((stack, value) -> value > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), LeatherOverhaul.BUNDLE.get());
        }

        @SubscribeEvent
        public static void registerSerializers(RegisterEvent event) {
            if (!(event.getRegistryKey() == ForgeRegistries.Keys.RECIPE_SERIALIZERS)) return;
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
        LeatherOverhaul.leatherDrops = Config.LEATHER_DROPS.get();
        LeatherOverhaul.kitBase = Config.KIT_BASE.get();
        LeatherOverhaul.kitBonus = Config.KIT_BONUS.get();
        LeatherOverhaul.kitRepair = Config.KIT_REPAIR.get();
        LeatherOverhaul.hideBundle = Config.BUNDLE_CRAFT_HIDE.get();
        LeatherOverhaul.leatherBundle = Config.BUNDLE_CRAFT_LEATHER.get();
    }
}