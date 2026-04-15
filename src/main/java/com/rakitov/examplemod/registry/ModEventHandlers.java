package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.client.renderer.RadioactiveMobRenderer;
import com.rakitov.examplemod.entity.RadioactiveMobEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class ModEventHandlers {
    private ModEventHandlers() {
    }

    @Mod.EventBusSubscriber(modid = AutoFarmCraftMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModBusEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.RADIOACTIVE_MOB.get(), RadioactiveMobEntity.createAttributes().build());
        }
    }

    @Mod.EventBusSubscriber(modid = AutoFarmCraftMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.RADIOACTIVE_MOB.get(), context -> new RadioactiveMobRenderer(context));
        }
    }
}
