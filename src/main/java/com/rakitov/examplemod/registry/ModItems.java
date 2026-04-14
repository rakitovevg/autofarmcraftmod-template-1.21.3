package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.item.RadioactivePistolItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AutoFarmCraftMod.MODID);

    public static final DeferredItem<Item> RADIOACTIVE_RESIDUE = ITEMS.registerSimpleItem("radioactive_residue", new Item.Properties());
    public static final DeferredItem<RadioactivePistolItem> RADIOACTIVE_PISTOL = ITEMS.registerItem("radioactive_pistol", RadioactivePistolItem::new);
    public static final DeferredItem<Item> MOONLEAF = ITEMS.registerSimpleItem("moonleaf", new Item.Properties());
    public static final DeferredItem<BlockItem> MOONLEAF_SEEDS = ITEMS.registerSimpleBlockItem("moonleaf_seeds", ModBlocks.MOONLEAF_CROP);

    public static final DeferredItem<BlockItem> RADIOACTIVE_TRACE_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.RADIOACTIVE_TRACE);

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
