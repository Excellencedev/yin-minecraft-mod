package com.adventuremod.progression;

import net.minecraft.entity.player.PlayerEntity;

/**
 * Implemented by mixin'd PlayerEntity so other code can fetch the
 * per-player progression data without dealing with capability-style APIs.
 */
public interface PlayerProgressionHolder {
    PlayerProgression adventuremod$getProgression();
}
