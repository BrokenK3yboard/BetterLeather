package com.brokenkeyboard.leatheroverhaul.datagen.loot;

import com.brokenkeyboard.leatheroverhaul.Config;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeatherDrops extends LootModifier {

    public LeatherDrops(LootItemCondition[] conditions) {
        super(conditions);
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (Config.LEATHER_DROPS.get() <= 0) return generatedLoot;
        generatedLoot.add(new ItemStack(Items.LEATHER, Config.LEATHER_DROPS.get()));
        return generatedLoot;
    }

    public static class LeatherDropsSerializer extends GlobalLootModifierSerializer<LeatherDrops> {

        @Override
        public LeatherDrops read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new LeatherDrops(conditions);
        }

        @Override
        public JsonObject write(LeatherDrops instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}