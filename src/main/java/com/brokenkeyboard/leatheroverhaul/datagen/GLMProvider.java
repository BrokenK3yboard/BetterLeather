package com.brokenkeyboard.leatheroverhaul.datagen;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import com.brokenkeyboard.leatheroverhaul.datagen.loot.LeatherDrops;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class GLMProvider extends GlobalLootModifierProvider {

    public GLMProvider(PackOutput output) {
        super(output, LeatherOverhaul.MOD_ID);
    }

    @Override
    protected void start() {
        add("leather_drops", new LeatherDrops(new LootItemCondition[] {
                new AnyOfCondition.Builder(
                        new LootTableIdCondition.Builder(new ResourceLocation("minecraft:entities/cow")),
                        new LootTableIdCondition.Builder(new ResourceLocation("minecraft:entities/mooshroom"))
                ).build()
        }));
    }
}