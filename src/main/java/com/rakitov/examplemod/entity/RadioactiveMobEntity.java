package com.rakitov.examplemod.entity;

import com.rakitov.examplemod.config.AutoFarmConfig;
import com.rakitov.examplemod.entity.ai.RadioactiveRangedGoal;
import com.rakitov.examplemod.registry.ModBlocks;
import com.rakitov.examplemod.registry.ModEffects;
import com.rakitov.examplemod.util.HitscanUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RadioactiveMobEntity extends Monster {
    private int trailTickCounter = 0;

    public RadioactiveMobEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, AutoFarmConfig.RADIOACTIVE_MOB_MELEE_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.ARMOR, 3.0D);
    }

    public static boolean checkRadioactiveMobSpawnRules(
            EntityType<RadioactiveMobEntity> entityType,
            ServerLevelAccessor level,
            EntitySpawnReason spawnType,
            BlockPos pos,
            net.minecraft.util.RandomSource random
    ) {
        return Monster.isDarkEnoughToSpawn(level, pos, random) && Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new RadioactiveRangedGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide) {
            return;
        }

        trailTickCounter++;
        if (trailTickCounter < AutoFarmConfig.RADIOACTIVE_TRAIL_PLACE_INTERVAL) {
            return;
        }
        trailTickCounter = 0;
        placeTrail();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, net.minecraft.world.entity.Entity target) {
        boolean success = super.doHurtTarget(level, target);
        if (success && target instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(ModEffects.RADIATION, AutoFarmConfig.RADIATION_DURATION_TICKS, 0));
        }
        return success;
    }

    public void performRangedAttack(LivingEntity target) {
        LivingEntity hit = HitscanUtil.findTarget(this, AutoFarmConfig.RADIOACTIVE_MOB_RANGED_DISTANCE, candidate -> candidate == target);
        if (hit == null) {
            return;
        }

        HitscanUtil.damageTarget(this, hit, AutoFarmConfig.RADIOACTIVE_MOB_RANGED_DAMAGE);
        hit.addEffect(new MobEffectInstance(ModEffects.RADIATION, AutoFarmConfig.RADIATION_DURATION_TICKS, 0));
    }

    private void placeTrail() {
        BlockPos pos = this.blockPosition().below();
        BlockState current = level().getBlockState(pos);
        BlockState trace = ModBlocks.RADIOACTIVE_TRACE.get().defaultBlockState();

        if (current.isAir() || current.is(BlockTags.REPLACEABLE) || current.is(Blocks.SHORT_GRASS) || current.is(Blocks.TALL_GRASS)) {
            level().setBlock(pos, trace, 3);
        }
    }
}
