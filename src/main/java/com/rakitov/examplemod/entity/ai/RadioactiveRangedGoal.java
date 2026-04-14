package com.rakitov.examplemod.entity.ai;

import com.rakitov.examplemod.config.AutoFarmConfig;
import com.rakitov.examplemod.entity.RadioactiveMobEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RadioactiveRangedGoal extends Goal {
    private final RadioactiveMobEntity mob;
    private int attackCooldown;

    public RadioactiveRangedGoal(RadioactiveMobEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void stop() {
        attackCooldown = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }

        double distanceSqr = mob.distanceToSqr(target);
        double attackRange = AutoFarmConfig.RADIOACTIVE_MOB_RANGED_DISTANCE;
        boolean inRange = distanceSqr <= attackRange * attackRange;
        boolean hasSight = mob.getSensing().hasLineOfSight(target);

        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (!inRange) {
            mob.getNavigation().moveTo(target, 1.1D);
        } else {
            mob.getNavigation().stop();
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        if (inRange && hasSight && attackCooldown <= 0) {
            mob.performRangedAttack(target);
            attackCooldown = AutoFarmConfig.RADIOACTIVE_MOB_RANGED_COOLDOWN;
        }
    }
}
