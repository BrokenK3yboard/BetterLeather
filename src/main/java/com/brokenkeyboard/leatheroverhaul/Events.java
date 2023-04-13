package com.brokenkeyboard.leatheroverhaul;

import com.brokenkeyboard.leatheroverhaul.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.leatheroverhaul.datagen.conditions.LeatherBundleCondition;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class Events {

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

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

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModClientEvents {

        @SubscribeEvent
        public static void colorItems(ColorHandlerEvent.Item event) {
            event.getItemColors().register((stack, value) -> value > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack), LeatherOverhaul.BUNDLE.get());
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