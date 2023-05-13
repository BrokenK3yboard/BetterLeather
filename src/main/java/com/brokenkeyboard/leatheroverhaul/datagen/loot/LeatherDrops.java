package com.brokenkeyboard.leatheroverhaul.datagen.loot;

import com.brokenkeyboard.leatheroverhaul.Config;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class LeatherDrops extends LootModifier {
    public static final Supplier<Codec<LeatherDrops>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(Codec.DOUBLE
            .fieldOf("chance").forGetter(m -> m.chance)).apply(inst, LeatherDrops::new)));

    private final double chance;

    public LeatherDrops(LootItemCondition[] conditionsIn, double chance) {
        super(conditionsIn);
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (Config.LEATHER_DROPS.get() == 0) return generatedLoot;
        Random rand = new Random();
        double rng = rand.nextDouble();

        if (rng < chance)
            generatedLoot.add(new ItemStack(Items.LEATHER, Config.LEATHER_DROPS.get()));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}