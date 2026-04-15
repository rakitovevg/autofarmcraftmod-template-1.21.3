package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.block.MoonleafCropBlock;
import com.rakitov.examplemod.block.RadioactiveTraceBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, AutoFarmCraftMod.MODID);

    public static final RegistryObject<RadioactiveTraceBlock> RADIOACTIVE_TRACE = BLOCKS.register(
            "radioactive_trace",
            () -> new RadioactiveTraceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0.3F)
                    .sound(SoundType.SLIME_BLOCK)
                    .randomTicks())
    );

    public static final RegistryObject<MoonleafCropBlock> MOONLEAF_CROP = BLOCKS.register(
            "moonleaf_crop",
            () -> new MoonleafCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP))
    );

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
