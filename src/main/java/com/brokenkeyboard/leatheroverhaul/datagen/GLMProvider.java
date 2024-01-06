package com.brokenkeyboard.leatheroverhaul.datagen;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import com.brokenkeyboard.leatheroverhaul.datagen.loot.LeatherDrops;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class GLMProvider extends GlobalLootModifierProvider {

    public GLMProvider(DataGenerator generator) {
        super(generator, LeatherOverhaul.MOD_ID);
    }

    @Override
    protected void start() {
        add("leather_drops", new LeatherDrops(new LootItemCondition[] {
                new AlternativeLootItemCondition.Builder(
                        new LootTableIdCondition.Builder(new ResourceLocation("minecraft:entities/cow")),
                        new LootTableIdCondition.Builder(new ResourceLocation("minecraft:entities/mooshroom"))
                ).build()
        }));
    }
}