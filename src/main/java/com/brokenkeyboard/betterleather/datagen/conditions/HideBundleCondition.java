package com.brokenkeyboard.betterleather.datagen.conditions;

import com.brokenkeyboard.betterleather.BetterLeather;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

public class HideBundleCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(BetterLeather.MOD_ID, "hide_bundle_enabled");
    public static final ConditionSerializer<HideBundleCondition> SERIALIZER = new ConditionSerializer<>(NAME, HideBundleCondition::new);

    public HideBundleCondition() {}

    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return BetterLeather.hideBundle;
    }

    @Override
    public boolean test() {
        return false;
    }
}