package com.rakitov.examplemod;

import com.rakitov.examplemod.handler.AutoCraftHandler;
import com.rakitov.examplemod.handler.AutoHarvestHandler;
import com.rakitov.examplemod.handler.AutoPlantHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class AutoFarmCraftModClient {
    private static AutoCraftHandler craftHandler;
    private static AutoHarvestHandler harvestHandler;

    private static AutoPlantHandler plantHandler;

    // ==================== КЛАВИШИ ====================
    public static final KeyMapping TOGGLE_HARVEST = new KeyMapping(
            "key.autofarmcraft.toggle_harvest",
            GLFW.GLFW_KEY_H,
            "key.categories.autofarmcraft"
    );
    public static final KeyMapping TOGGLE_PLANT = new KeyMapping(
            "key.autofarmcraft.toggle_plant",
            GLFW.GLFW_KEY_J,
            "key.categories.autofarmcraft"
    );
    public static final KeyMapping TOGGLE_CRAFT = new KeyMapping(
            "key.autofarmcraft.toggle_craft",
            GLFW.GLFW_KEY_K,
            "key.categories.autofarmcraft"
    );

    // ==================== РЕГИСТРАЦИЯ ====================
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HARVEST);
        event.register(TOGGLE_PLANT);
        event.register(TOGGLE_CRAFT);
    }

    /**
     * Инициализация клиентской части мода
     */
    public static void init() {
        MinecraftForge.EVENT_BUS.register(AutoFarmCraftModClient.class);
        craftHandler = new AutoCraftHandler();
        harvestHandler = new AutoHarvestHandler();
        plantHandler = new AutoPlantHandler();

        System.out.println("§a[AutoFarmCraft] Client initialized!");
    }

    /**
     * Тик клиента каждый кадр
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null || mc.screen != null) {
            return;
        }

        // Обработка нажатия клавиши H
        if (TOGGLE_HARVEST.consumeClick()) {
            AutoFarmCraftMod.autoHarvestEnabled = !AutoFarmCraftMod.autoHarvestEnabled;

            String status = AutoFarmCraftMod.autoHarvestEnabled ? "§aВКЛЮЧЁН" : "§cВЫКЛЮЧЕН";

            mc.gui.setOverlayMessage(
                    Component.literal("§6[AutoFarmCraft] §fAuto Harvest: " + status),
                    false
            );
        }

        if (TOGGLE_PLANT.consumeClick()) {
            AutoFarmCraftMod.autoPlantEnabled = !AutoFarmCraftMod.autoPlantEnabled;

            String status = AutoFarmCraftMod.autoPlantEnabled ? "§aВКЛЮЧЁН" : "§cВЫКЛЮЧЕН";

            mc.gui.setOverlayMessage(
                    Component.literal("§6[AutoFarmCraft] §fAuto Plant: " + status),
                    false
            );
        }

        if (TOGGLE_CRAFT.consumeClick()) {
            AutoFarmCraftMod.autoCraftEnabled = !AutoFarmCraftMod.autoCraftEnabled;

            String status = AutoFarmCraftMod.autoCraftEnabled ? "§aВКЛЮЧЁН" : "§cВЫКЛЮЧЕН";

            mc.gui.setOverlayMessage(
                    Component.literal("§6[AutoFarmCraft] §fAuto Craft: " + status),
                    false
            );
        }
    }
}
