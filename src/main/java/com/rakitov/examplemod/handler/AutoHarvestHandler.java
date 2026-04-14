package com.rakitov.examplemod.handler;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.config.AutoFarmConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AutoHarvestHandler {
    private final Minecraft mc = Minecraft.getInstance();
    private int tickCounter = 0;

    public AutoHarvestHandler() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        if (!AutoFarmCraftMod.autoHarvestEnabled) return;
        if (mc.player == null || mc.level == null || mc.screen != null) return;

        tickCounter++;
        if (tickCounter % AutoFarmConfig.HARVEST_TICK_INTERVAL != 0) return;

        BlockPos playerPos = mc.player.blockPosition();

        for (int x = -AutoFarmConfig.HARVEST_RADIUS; x <= AutoFarmConfig.HARVEST_RADIUS; x++) {
            for (int z = -AutoFarmConfig.HARVEST_RADIUS; z <= AutoFarmConfig.HARVEST_RADIUS; z++) {
                for (int y = AutoFarmConfig.HARVEST_MIN_Y_OFFSET; y <= AutoFarmConfig.HARVEST_MAX_Y_OFFSET; y++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    BlockState state = mc.level.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block instanceof CropBlock crop) {
                        if (crop.isMaxAge(state)) {
                            mc.gameMode.destroyBlock(pos);
                            return;
                        }
                    }
                }
            }
        }
    }
}
