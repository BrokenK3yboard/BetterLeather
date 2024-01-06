package com.brokenkeyboard.leatheroverhaul;

import com.brokenkeyboard.leatheroverhaul.datagen.conditions.HideBundleCondition;
import com.brokenkeyboard.leatheroverhaul.datagen.conditions.LeatherBundleCondition;
import com.brokenkeyboard.leatheroverhaul.item.LeatherArmor;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@SuppressWarnings("unused")
public class Events {

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerSerializers(RegisterEvent event) {
            if (!(event.getRegistryKey() == ForgeRegistries.Keys.RECIPE_SERIALIZERS)) return;
            CraftingHelper.register(HideBundleCondition.SERIALIZER);
            CraftingHelper.register(LeatherBundleCondition.SERIALIZER);
        }

        @SubscribeEvent
        public static void setup(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(LeatherOverhaul.LEATHER_HELMET.get(),
                        new ResourceLocation(LeatherOverhaul.MOD_ID, "bonus_armor"),
                        (stack, world, living, id) -> living != null && LeatherArmor.getBonusArmor(stack) > 0 ? 1.0F : 0.0F);

                ItemProperties.register(LeatherOverhaul.LEATHER_CHESTPLATE.get(),
                        new ResourceLocation(LeatherOverhaul.MOD_ID, "bonus_armor"),
                        (stack, world, living, id) -> living != null && LeatherArmor.getBonusArmor(stack) > 0 ? 1.0F : 0.0F);

                ItemProperties.register(LeatherOverhaul.LEATHER_LEGGINGS.get(),
                        new ResourceLocation(LeatherOverhaul.MOD_ID, "bonus_armor"),
                        (stack, world, living, id) -> living != null && LeatherArmor.getBonusArmor(stack) > 0 ? 1.0F : 0.0F);

                ItemProperties.register(LeatherOverhaul.LEATHER_BOOTS.get(),
                        new ResourceLocation(LeatherOverhaul.MOD_ID, "bonus_armor"),
                        (stack, world, living, id) -> living != null && LeatherArmor.getBonusArmor(stack) > 0 ? 1.0F : 0.0F);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = LeatherOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void colorItems(RegisterColorHandlersEvent.Item event) {
            event.register((stack, value) -> value > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), LeatherOverhaul.BUNDLE.get());
        }

        @SubscribeEvent
        public static void renderDurability(RegisterItemDecorationsEvent event) {
            event.register(LeatherOverhaul.LEATHER_HELMET.get(), RenderDurability.INSTANCE);
            event.register(LeatherOverhaul.LEATHER_CHESTPLATE.get(), RenderDurability.INSTANCE);
            event.register(LeatherOverhaul.LEATHER_LEGGINGS.get(), RenderDurability.INSTANCE);
            event.register(LeatherOverhaul.LEATHER_BOOTS.get(), RenderDurability.INSTANCE);
        }
    }
}