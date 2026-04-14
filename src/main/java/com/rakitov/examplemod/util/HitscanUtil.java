package com.rakitov.examplemod.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public final class HitscanUtil {
    private HitscanUtil() {
    }

    public static LivingEntity findTarget(LivingEntity shooter, double range, Predicate<Entity> filter) {
        Vec3 start = shooter.getEyePosition();
        Vec3 look = shooter.getLookAngle();
        Vec3 end = start.add(look.scale(range));

        BlockHitResult blockHit = shooter.level().clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                shooter
        ));

        double blockDistance = range;
        if (blockHit.getType() != HitResult.Type.MISS) {
            blockDistance = start.distanceTo(blockHit.getLocation());
            end = blockHit.getLocation();
        }

        AABB searchBox = shooter.getBoundingBox().expandTowards(look.scale(blockDistance)).inflate(1.0D);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                shooter,
                start,
                end,
                searchBox,
                filter,
                blockDistance * blockDistance
        );

        if (entityHit != null && entityHit.getEntity() instanceof LivingEntity livingEntity) {
            return livingEntity;
        }
        return null;
    }

    public static void damageTarget(LivingEntity attacker, LivingEntity target, float damage) {
        Level level = attacker.level();
        if (level instanceof ServerLevel serverLevel) {
            if (attacker instanceof Player player) {
                target.hurtServer(serverLevel, level.damageSources().playerAttack(player), damage);
            } else {
                target.hurtServer(serverLevel, level.damageSources().mobAttack(attacker), damage);
            }
        } else {
            target.hurt(level.damageSources().generic(), damage);
        }
    }
}
