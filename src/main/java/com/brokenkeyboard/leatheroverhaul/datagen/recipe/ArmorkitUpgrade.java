package com.brokenkeyboard.leatheroverhaul.datagen.recipe;

import com.brokenkeyboard.leatheroverhaul.Config;
import com.brokenkeyboard.leatheroverhaul.item.LeatherArmor;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.getPotionEffect;
import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.setPotionEffect;

public class ArmorkitUpgrade extends SmithingTransformRecipe {
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public ArmorkitUpgrade(ResourceLocation location, Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
        super(location, template, base, addition, result);
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        boolean result = super.matches(container, level);
        boolean kitMax = !(container.getItem(0).isDamaged());
        ItemStack armor = container.getItem(1);
        boolean notMax = LeatherArmor.getBonusArmor(armor) < (int)(armor.getMaxDamage() * (Config.KIT_BASE.get() + Config.KIT_BONUS.get()));
        boolean damaged = (Config.KIT_REPAIR.get() == 0 || armor.isDamaged());
        return result && kitMax && (notMax || damaged);
    }

    @NotNull
    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        ItemStack result = super.assemble(container, access);
        int amount = (int)(result.getMaxDamage() * (Config.KIT_BASE.get() + Config.KIT_BONUS.get()));
        int repairCount = (int)(result.getMaxDamage() * Config.KIT_REPAIR.get());
        LeatherArmor.setBonusArmor(result, amount);
        LeatherArmor.setBonusArmorMax(result, amount);
        result.setDamageValue(result.getDamageValue() - repairCount);

        MobEffectInstance effect = getPotionEffect(container.getItem(0));

        if (effect != null) {
            setPotionEffect(result, effect);
        } else {
            result.removeTagKey("potion_effect");
        }
        return result;
    }

    public static class Serializer implements RecipeSerializer<ArmorkitUpgrade> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ArmorkitUpgrade fromJson(ResourceLocation location, JsonObject object) {
            Ingredient armorkit = Ingredient.fromJson(GsonHelper.getNonNull(object, "template"));
            Ingredient armor = Ingredient.fromJson(GsonHelper.getNonNull(object, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getNonNull(object, "addition"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(object, "result"));
            return new ArmorkitUpgrade(location, armorkit, armor, addition, result);
        }

        @Override
        public ArmorkitUpgrade fromNetwork(ResourceLocation location, FriendlyByteBuf buf) {
            Ingredient armorkit = Ingredient.fromNetwork(buf);
            Ingredient armor = Ingredient.fromNetwork(buf);
            Ingredient addition = Ingredient.fromNetwork(buf);
            ItemStack result = buf.readItem();
            return new ArmorkitUpgrade(location, armorkit, armor, addition, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ArmorkitUpgrade recipe) {
            recipe.template.toNetwork(buf);
            recipe.base.toNetwork(buf);
            recipe.addition.toNetwork(buf);
            buf.writeItem(recipe.result);
        }
    }
}