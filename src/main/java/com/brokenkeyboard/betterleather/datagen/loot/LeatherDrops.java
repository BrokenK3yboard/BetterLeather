package com.brokenkeyboard.betterleather.datagen.loot;

import com.brokenkeyboard.betterleather.BetterLeather;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class LeatherDrops extends LootModifier {

    private final double chance;

    public LeatherDrops(LootItemCondition[] conditionsIn, double chance) {
        super(conditionsIn);
        this.chance = chance;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if(BetterLeather.leatherDrops == 0) return generatedLoot;
        Random rand = new Random();
        double rng = rand.nextDouble();

        if(rng < chance)
            generatedLoot.add(new ItemStack(Items.LEATHER, BetterLeather.leatherDrops));

        return generatedLoot;
    }

    public static class LeatherDropsSerializer extends GlobalLootModifierSerializer<LeatherDrops> {

        @Override
        public LeatherDrops read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            final double chance = GsonHelper.getAsDouble(object, "chance");
            return new LeatherDrops(ailootcondition, chance);
        }

        @Override
        public JsonObject write(LeatherDrops instance) {
            return new JsonObject();
        }
    }
}