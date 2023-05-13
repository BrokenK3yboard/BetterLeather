package com.brokenkeyboard.leatheroverhaul;

import com.brokenkeyboard.leatheroverhaul.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.leatheroverhaul.datagen.conditions.LeatherBundleCondition;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class Events {

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void registerSerializers(RegisterEvent event) {
            if (!(event.getRegistryKey() == ForgeRegistries.Keys.RECIPE_SERIALIZERS)) return;
            CraftingHelper.register(HideBundleCondition.SERIALIZER);
            CraftingHelper.register(LeatherBundleCondition.SERIALIZER);
        }
    }

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModClientEvents {

        @SubscribeEvent
        public static void colorItems(RegisterColorHandlersEvent.Item event) {
            event.register((stack, value) -> value > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), LeatherOverhaul.BUNDLE.get());
        }
    }
}