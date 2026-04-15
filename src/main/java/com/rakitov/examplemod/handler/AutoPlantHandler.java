package com.rakitov.examplemod.handler;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.config.AutoFarmConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoPlantHandler {
    private final Minecraft mc = Minecraft.getInstance();
    private int tickCounter = 0;

    public AutoPlantHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!AutoFarmCraftMod.autoPlantEnabled) return;
        if (mc.player == null || mc.level == null || mc.screen != null) return;

        tickCounter++;
        if (tickCounter % AutoFarmConfig.PLANT_TICK_INTERVAL != 0) return;

        BlockPos playerPos = mc.player.blockPosition();
        ItemStack heldStack = mc.player.getMainHandItem();
        Item heldItem = heldStack.getItem();

        if (!isSupportedSeed(heldItem)) {
            return;
        }

        for (int x = -AutoFarmConfig.PLANT_RADIUS; x <= AutoFarmConfig.PLANT_RADIUS; x++) {
            for (int z = -AutoFarmConfig.PLANT_RADIUS; z <= AutoFarmConfig.PLANT_RADIUS; z++) {
                BlockPos farmlandPos = playerPos.offset(x, -1, z);
                BlockPos plantPos = farmlandPos.above();

                BlockState below = mc.level.getBlockState(farmlandPos);
                BlockState above = mc.level.getBlockState(plantPos);

                if (below.is(Blocks.FARMLAND) && above.isAir()) {
                    BlockHitResult hitResult = new BlockHitResult(
                            mc.player.position().add(0, 1, 0),
                            Direction.UP,
                            farmlandPos,
                            false
                    );

                    InteractionResult result = mc.gameMode.useItemOn(
                            mc.player,
                            InteractionHand.MAIN_HAND,
                            hitResult
                    );

                    if (result.consumesAction()) {
                        return;
                    }
                }
            }
        }
    }

    private boolean isSupportedSeed(Item item) {
        return item == Items.WHEAT_SEEDS
                || item == Items.CARROT
                || item == Items.POTATO
                || item == Items.BEETROOT_SEEDS;
    }
}
