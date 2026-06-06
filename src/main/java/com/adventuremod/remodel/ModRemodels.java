package com.adventuremod.remodel;

import com.adventuremod.AdventureMod;

/**
 * Manifest Remodels & Animations.
 *
 * The mod does not override vanilla entity models, but it does provide
 * custom models for its blocks, items, and entities:
 *  - Block models: hunter_table, butchering_table, wild_berry_bush (3 stages)
 *  - Item models: all mod items are item/generated with custom textures
 *  - Entity textures: wild_boar, deer, guard_villager, rideable_boar,
 *    forest_fox, stag_spirit, forest_guardian
 */
public class ModRemodels {
    public static void registerRemodels() {
        AdventureMod.LOGGER.info("Registering Remodels & Animations for " + AdventureMod.MOD_ID);
    }
}
