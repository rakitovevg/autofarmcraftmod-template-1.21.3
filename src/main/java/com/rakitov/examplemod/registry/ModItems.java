package com.rakitov.examplemod.registry;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.item.RadioactivePistolItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, AutoFarmCraftMod.MODID);

    public static final RegistryObject<Item> RADIOACTIVE_RESIDUE = ITEMS.register("radioactive_residue", () -> new Item(new Item.Properties()));
    public static final RegistryObject<RadioactivePistolItem> RADIOACTIVE_PISTOL = ITEMS.register("radioactive_pistol", () -> new RadioactivePistolItem(new Item.Properties()));
    public static final RegistryObject<Item> MOONLEAF = ITEMS.register("moonleaf", () -> new Item(new Item.Properties()));
    public static final RegistryObject<BlockItem> MOONLEAF_SEEDS = ITEMS.register("moonleaf_seeds",
            () -> new BlockItem(ModBlocks.MOONLEAF_CROP.get(), new Item.Properties().useItemDescriptionPrefix()));

    public static final RegistryObject<BlockItem> RADIOACTIVE_TRACE_ITEM = ITEMS.register("radioactive_trace",
            () -> new BlockItem(ModBlocks.RADIOACTIVE_TRACE.get(), new Item.Properties().useItemDescriptionPrefix()));

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
