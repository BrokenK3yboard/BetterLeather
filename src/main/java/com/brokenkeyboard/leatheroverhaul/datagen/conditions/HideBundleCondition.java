package com.brokenkeyboard.leatheroverhaul.datagen.conditions;

import com.brokenkeyboard.leatheroverhaul.Config;
import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

public class HideBundleCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(LeatherOverhaul.MOD_ID, "hide_bundle_enabled");
    public static final ConditionSerializer<HideBundleCondition> SERIALIZER = new ConditionSerializer<>(NAME, HideBundleCondition::new);

    public HideBundleCondition() {}

    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return Config.BUNDLE_CRAFT_HIDE.get();
    }

    @Override
    public boolean test() {
        return false;
    }
}