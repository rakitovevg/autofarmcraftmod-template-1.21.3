package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.client.renderer.RadioactiveMobRenderer;
import com.rakitov.examplemod.entity.RadioactiveMobEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public final class ModEventHandlers {
    private ModEventHandlers() {
    }

    @EventBusSubscriber(modid = AutoFarmCraftMod.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonModBusEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.RADIOACTIVE_MOB.get(), RadioactiveMobEntity.createAttributes().build());
        }
    }

    @EventBusSubscriber(modid = AutoFarmCraftMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.RADIOACTIVE_MOB.get(), context -> new RadioactiveMobRenderer(context));
        }
    }
}
