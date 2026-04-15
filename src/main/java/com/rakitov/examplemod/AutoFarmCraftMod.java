package com.rakitov.examplemod;

import com.rakitov.examplemod.registry.ModBlocks;
import com.rakitov.examplemod.registry.ModEffects;
import com.rakitov.examplemod.registry.ModEntities;
import com.rakitov.examplemod.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod(AutoFarmCraftMod.MODID)
public class AutoFarmCraftMod {
    public static final String MODID = "autofarmcraftmod";

    public static boolean autoHarvestEnabled = false;
    public static boolean autoPlantEnabled = false;
    public static boolean autoCraftEnabled = false;

    public AutoFarmCraftMod(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        modEventBus.addListener(AutoFarmCraftModClient::registerKeys);
        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        AutoFarmCraftModClient.init();
    }
}
