package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.block.MoonleafCropBlock;
import com.rakitov.examplemod.block.RadioactiveTraceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AutoFarmCraftMod.MODID);

    public static final DeferredBlock<RadioactiveTraceBlock> RADIOACTIVE_TRACE = BLOCKS.registerBlock(
            "radioactive_trace",
            RadioactiveTraceBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0.3F)
                    .sound(SoundType.SLIME_BLOCK)
                    .randomTicks()
    );

    public static final DeferredBlock<MoonleafCropBlock> MOONLEAF_CROP = BLOCKS.registerBlock(
            "moonleaf_crop",
            MoonleafCropBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
    );

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
