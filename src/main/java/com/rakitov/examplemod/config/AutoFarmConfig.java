package com.rakitov.examplemod.config;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public final class AutoFarmConfig {
    private AutoFarmConfig() {
    }

    public static final int HARVEST_RADIUS = 3;
    public static final int HARVEST_MIN_Y_OFFSET = -1;
    public static final int HARVEST_MAX_Y_OFFSET = 2;
    public static final int HARVEST_TICK_INTERVAL = 5;

    public static final int PLANT_RADIUS = 4;
    public static final int PLANT_TICK_INTERVAL = 6;

    public static final int CRAFT_TICK_INTERVAL = 10;

    public static final float RADIATION_HEART_DAMAGE = 2.0F;
    public static final int RADIATION_TICK_INTERVAL = 40;
    public static final int RADIATION_DURATION_TICKS = 120;

    public static final float RADIOACTIVE_MOB_MELEE_DAMAGE = 4.0F;
    public static final float RADIOACTIVE_MOB_RANGED_DAMAGE = 3.0F;
    public static final double RADIOACTIVE_MOB_RANGED_DISTANCE = 12.0D;
    public static final int RADIOACTIVE_MOB_RANGED_COOLDOWN = 40;
    public static final int RADIOACTIVE_TRAIL_PLACE_INTERVAL = 15;

    public static final float RADIOACTIVE_PISTOL_DAMAGE = 6.0F;
    public static final int RADIOACTIVE_PISTOL_COOLDOWN = 20;
    public static final double RADIOACTIVE_PISTOL_RANGE = 24.0D;

    /**
     * Craft output items in priority order.
     * The first craftable result item in this list will be used.
     */
    public static final List<ResourceLocation> CRAFT_RESULT_PRIORITY = buildRecipePriority();

    private static List<ResourceLocation> buildRecipePriority() {
        List<ResourceLocation> result = new ArrayList<>();
        addRecipeId(result, "minecraft:bread");
        addRecipeId(result, "minecraft:hay_block");
        addRecipeId(result, "minecraft:paper");
        return List.copyOf(result);
    }

    private static void addRecipeId(List<ResourceLocation> target, String id) {
        try {
            target.add(ResourceLocation.parse(id));
        } catch (RuntimeException ignored) {
            // Keep defaults robust if a recipe id is malformed.
        }
    }
}
