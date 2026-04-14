package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.effect.RadiationMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, AutoFarmCraftMod.MODID);

    public static final DeferredHolder<MobEffect, RadiationMobEffect> RADIATION = EFFECTS.register("radiation", RadiationMobEffect::new);

    private ModEffects() {
    }

    public static void register(IEventBus modEventBus) {
        EFFECTS.register(modEventBus);
    }
}
