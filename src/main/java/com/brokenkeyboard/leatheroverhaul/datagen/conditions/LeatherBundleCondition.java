package com.brokenkeyboard.leatheroverhaul.datagen.conditions;

import com.brokenkeyboard.leatheroverhaul.LeatherOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

public class LeatherBundleCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(LeatherOverhaul.MOD_ID, "leather_bundle_enabled");
    public static final ConditionSerializer<LeatherBundleCondition> SERIALIZER = new ConditionSerializer<>(NAME, LeatherBundleCondition::new);

    public LeatherBundleCondition() {}

    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return LeatherOverhaul.leatherBundle;
    }
}