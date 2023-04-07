package com.brokenkeyboard.leatheroverhaul;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static ForgeConfigSpec.IntValue LEATHER_DROPS;
    public static ForgeConfigSpec.BooleanValue BUNDLE_CRAFT_HIDE;
    public static ForgeConfigSpec.BooleanValue BUNDLE_CRAFT_LEATHER;
    public static ForgeConfigSpec.DoubleValue KIT_BASE;
    public static ForgeConfigSpec.DoubleValue KIT_BONUS;
    public static ForgeConfigSpec.DoubleValue KIT_REPAIR;

    public static void registerConfig() {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();

        LEATHER_DROPS = CONFIG_BUILDER
                .comment("The minimum amount of leather dropped by cows and mooshrooms.")
                .defineInRange("Extra leather drops", 2,0,4);

        BUNDLE_CRAFT_HIDE = CONFIG_BUILDER
                .comment("If enabled, bundles are craftable with rabbit hide.")
                .define("Hide bundles", true);

        BUNDLE_CRAFT_LEATHER = CONFIG_BUILDER
                .comment("If enabled, bundles are craftable with leather.")
                .define("Leather bundles", true);

        KIT_BASE = CONFIG_BUILDER
                .comment("The percentage of leather armor durability given as bonus durability when using an armor kit.")
                .defineInRange("Armor kit bonus", 0.5, 0, 1.0);

        KIT_BONUS = CONFIG_BUILDER
                .comment("The percentage of leather armor durability given as an additive bonus when using armor kits in a smithing table.")
                .defineInRange("Armor kit smithing bonus", 0.5, 0, 1.0);

        KIT_REPAIR = CONFIG_BUILDER
                .comment("The percentage of durability armor kits repair when applied with a smithing table.")
                .defineInRange("Armor kit repair", 0.15, 0, 0.25);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_BUILDER.build());
    }
}