package com.rakitov.examplemod.handler;

import com.rakitov.examplemod.AutoFarmCraftMod;
import com.rakitov.examplemod.config.AutoFarmConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;

public class AutoCraftHandler {
    private final Minecraft mc = Minecraft.getInstance();
    private int tickCounter = 0;

    public AutoCraftHandler() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        if (!AutoFarmCraftMod.autoCraftEnabled) return;
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;
        if (!(mc.screen instanceof CraftingScreen)) return;
        if (!(mc.player.containerMenu instanceof CraftingMenu craftingMenu)) return;

        tickCounter++;
        if (tickCounter % AutoFarmConfig.CRAFT_TICK_INTERVAL != 0) return;

        StackedItemContents stackedContents = new StackedItemContents();
        mc.player.getInventory().fillStackedContents(stackedContents);
        ContextMap contextMap = SlotDisplayContext.fromLevel(mc.level);

        List<ResourceLocation> resultPriority = AutoFarmConfig.CRAFT_RESULT_PRIORITY;
        for (ResourceLocation resultId : resultPriority) {
            RecipeDisplayId displayId = findCraftableDisplayId(resultId, craftingMenu, stackedContents, contextMap);
            if (displayId == null) continue;

            mc.gameMode.handlePlaceRecipe(craftingMenu.containerId, displayId, true);
            if (!craftingMenu.getSlot(0).hasItem()) continue;

            mc.gameMode.handleInventoryMouseClick(craftingMenu.containerId, 0, 0, ClickType.QUICK_MOVE, mc.player);
            return;
        }
    }

    private RecipeDisplayId findCraftableDisplayId(
            ResourceLocation targetResult,
            CraftingMenu craftingMenu,
            StackedItemContents stackedContents,
            ContextMap contextMap
    ) {
        for (var collection : mc.player.getRecipeBook().getCollections()) {
            for (RecipeDisplayEntry entry : collection.getRecipes()) {
                if (!canDisplay(entry.display(), craftingMenu)) continue;
                if (!entry.canCraft(stackedContents)) continue;
                if (!matchesTargetResult(entry, targetResult, contextMap)) continue;
                return entry.id();
            }
        }
        return null;
    }

    private boolean matchesTargetResult(RecipeDisplayEntry entry, ResourceLocation targetResult, ContextMap contextMap) {
        for (var resultStack : entry.resultItems(contextMap)) {
            if (BuiltInRegistries.ITEM.getKey(resultStack.getItem()).equals(targetResult)) {
                return true;
            }
        }
        return false;
    }

    private boolean canDisplay(RecipeDisplay recipeDisplay, CraftingMenu craftingMenu) {
        int width = craftingMenu.getGridWidth();
        int height = craftingMenu.getGridHeight();

        return switch (recipeDisplay) {
            case ShapedCraftingRecipeDisplay shaped -> width >= shaped.width() && height >= shaped.height();
            case ShapelessCraftingRecipeDisplay shapeless -> width * height >= shapeless.ingredients().size();
            default -> false;
        };
    }
}
