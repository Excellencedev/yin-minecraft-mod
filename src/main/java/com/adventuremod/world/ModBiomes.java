package com.adventuremod.world;

import com.adventuremod.AdventureMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

/**
 * Manifest Biomes & Structures — Whispering Woods.
 *
 * The biome itself is defined as a data-driven biome at
 * {@code data/adventuremod/worldgen/biome/whispering_woods.json}.
 * That file places it in the world-gen biome registry, which makes the
 * biome addressable from commands like {@code /locate biome
 * adventuremod:whispering_woods} and {@code /setbiome}.
 *
 * This class exposes a typed {@link RegistryKey} for code that needs to
 * reference the biome (e.g. spawn filtering and future world-gen hooks)
 * without re-parsing identifiers everywhere.
 */
public final class ModBiomes {
    public static final RegistryKey<Biome> WHISPERING_WOODS = RegistryKey.of(
            RegistryKeys.BIOME,
            Identifier.of(AdventureMod.MOD_ID, "whispering_woods")
    );

    private ModBiomes() {
    }

    public static void registerBiomes() {
        AdventureMod.LOGGER.info("Registering Biomes for " + AdventureMod.MOD_ID);
    }
}
