package com.brokenkeyboard.leatheroverhaul.item;

import com.brokenkeyboard.leatheroverhaul.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.brokenkeyboard.leatheroverhaul.item.PotionKitUtils.*;

public class ArmorKitItem extends Item {

    public ArmorKitItem(Properties properties) {
        super(properties.defaultDurability(4));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(leatherArmorCount(player) > 0) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        List<ItemStack> armor = (List<ItemStack>) entity.getArmorSlots();
        int count = stack.getMaxDamage() - stack.getDamageValue();
        int armorCount = leatherArmorCount(entity);

        for (ItemStack a : armor) {
            if (a.getItem() instanceof LeatherArmor) {
                int bonusArmor = (int)(a.getMaxDamage() * Config.KIT_BASE.get());
                if(count > 0 && LeatherArmor.getBonusArmor(a) < bonusArmor) {
                    LeatherArmor.setBonusArmor(a, bonusArmor);
                    LeatherArmor.setBonusArmorMax(a, bonusArmor);

                    if(getPotionEffect(stack) != null) {
                        MobEffectInstance effect = getPotionEffect(stack);
                        setPotionEffect(a, effect);
                    } else {
                        a.removeTagKey("potion_effect");
                    }
                    count--;
                }
            }
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.setDamageValue(stack.getDamageValue() + armorCount);
            if (stack.getDamageValue() >= stack.getMaxDamage())
                stack = ItemStack.EMPTY;
        }
        entity.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
        return stack;
    }

    public static int leatherArmorCount(LivingEntity entity) {
        List<ItemStack> armor = (List<ItemStack>) entity.getArmorSlots();
        int count = 0;
        for (ItemStack a : armor) {
            if (a.getItem() instanceof LeatherArmor) {
                int maxBonus = (int) (a.getMaxDamage() * Config.KIT_BASE.get());
                if (LeatherArmor.getBonusArmor(a) < maxBonus) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (getPotionEffect(stack) != null)
            displayPotionEffect(stack, components);
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public boolean isFoil(ItemStack stack) {
        return getPotionEffect(stack) != null;
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public boolean isRepairable(ItemStack stack) {
        return false;
    }
}