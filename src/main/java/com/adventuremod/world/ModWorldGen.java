package com.adventuremod.world;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
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

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.SURFACE_STRUCTURES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(AdventureMod.MOD_ID, "hunter_outpost"))
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                SpawnGroup.CREATURE,
                ModEntities.WILD_BOAR,
                60, 2, 4
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                SpawnGroup.CREATURE,
                ModEntities.DEER,
                80, 2, 5
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                SpawnGroup.MISC,
                ModEntities.GUARD_VILLAGER,
                5, 1, 1
        );
    }
}
