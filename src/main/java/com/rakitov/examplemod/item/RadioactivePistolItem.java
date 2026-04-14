package com.rakitov.examplemod.item;

import com.rakitov.examplemod.config.AutoFarmConfig;
import com.rakitov.examplemod.registry.ModEffects;
import com.rakitov.examplemod.util.HitscanUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RadioactivePistolItem extends Item {
    public RadioactivePistolItem(Properties properties) {
        super(properties.stacksTo(1).durability(600));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (player.getCooldowns().isOnCooldown(stack)) {
            return InteractionResult.FAIL;
        }

        LivingEntity target = HitscanUtil.findTarget(player, AutoFarmConfig.RADIOACTIVE_PISTOL_RANGE, this::isValidTarget);
        if (target != null) {
            HitscanUtil.damageTarget(player, target, AutoFarmConfig.RADIOACTIVE_PISTOL_DAMAGE);
            target.addEffect(new MobEffectInstance(ModEffects.RADIATION, AutoFarmConfig.RADIATION_DURATION_TICKS, 0));
        }

        player.getCooldowns().addCooldown(stack, AutoFarmConfig.RADIOACTIVE_PISTOL_COOLDOWN);
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(usedHand));
        return InteractionResult.SUCCESS;
    }

    private boolean isValidTarget(Entity entity) {
        return entity instanceof LivingEntity livingEntity && livingEntity.isAlive();
    }
}
