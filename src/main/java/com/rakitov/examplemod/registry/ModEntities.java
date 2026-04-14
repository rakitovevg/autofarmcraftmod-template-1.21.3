package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.entity.RadioactiveMobEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AutoFarmCraftMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<RadioactiveMobEntity>> RADIOACTIVE_MOB = ENTITY_TYPES.register(
            "radioactive_mob",
            key -> EntityType.Builder.of(RadioactiveMobEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.95F)
                    .clientTrackingRange(8)
                    .updateInterval(3)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, key))
    );

    private ModEntities() {
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
