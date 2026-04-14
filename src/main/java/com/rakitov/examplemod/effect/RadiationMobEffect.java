package com.rakitov.examplemod.effect;

import com.rakitov.examplemod.config.AutoFarmConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadiationMobEffect extends MobEffect {
    public RadiationMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x7AFF00);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        entity.hurtServer(level, entity.damageSources().magic(), AutoFarmConfig.RADIATION_HEART_DAMAGE);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int interval = Math.max(1, AutoFarmConfig.RADIATION_TICK_INTERVAL - (amplifier * 5));
        return duration % interval == 0;
    }
}
