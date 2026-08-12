package com.adventuremod.world;

import com.adventuremod.AdventureMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ModWorldGen {
    public static final Feature<DefaultFeatureConfig> HUNTER_OUTPOST_FEATURE = Registry.register(
            Registries.FEATURE,
            Identifier.of(AdventureMod.MOD_ID, "hunter_outpost"),
            new HunterOutpostFeature()
    );

    public static void registerWorldGen() {
        AdventureMod.LOGGER.info("Registering World Gen for " + AdventureMod.MOD_ID);
        AdventureMod.LOGGER.info("World generation biome injection is temporarily disabled while startup loading is stabilized.");
    }
}
