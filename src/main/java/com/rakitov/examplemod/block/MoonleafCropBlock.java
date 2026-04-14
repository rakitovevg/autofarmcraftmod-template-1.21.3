package com.rakitov.examplemod.block;

import com.rakitov.examplemod.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class MoonleafCropBlock extends CropBlock {
    public MoonleafCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MOONLEAF_SEEDS.get();
    }
}
