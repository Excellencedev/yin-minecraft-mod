package com.adventuremod.survival;

import com.adventuremod.progression.PlayerProgressionHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class ThirstManager {
    public static final int MAX = 20;
    private int thirstLevel = MAX;
    private float thirstSaturation = 5.0f;
    private int thirstTickTimer = 0;
    private int starveTickTimer = 0;

    public void tick(PlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) return;

        // Sprinting drains thirst: ~1 level every 4 seconds of sprinting
        // (80 ticks), four times the original pace. Thirst saturation acts as
        // a buffer first (mirroring vanilla hunger), so drinks with high
        // saturation delay the actual level drop.
        thirstTickTimer++;
        if (thirstTickTimer >= 80) {
            thirstTickTimer = 0;
            if (player.isSprinting()) {
                if (thirstSaturation > 0.0f) {
                    thirstSaturation = Math.max(0.0f, thirstSaturation - 1.5f);
                } else {
                    addThirst(-1);
                }
            }
        }

        // Starvation: once thirst is empty, take 1 damage every 4 seconds
        // (80 ticks) of game time, but only if the player can actually take
        // damage (peaceful/non-creative), so we don't spam damage ticks.
        if (thirstLevel <= 0 && thirstSaturation <= 0.0f) {
            starveTickTimer++;
            if (starveTickTimer >= 80) {
                starveTickTimer = 0;
                if (player.isAlive() && !player.isInvulnerableTo(player.getDamageSources().starve())) {
                    player.damage(player.getDamageSources().starve(), 1.0f);
                }
            }
        } else {
            starveTickTimer = 0;
        }
    }

    public void addThirst(int amount) {
        thirstLevel = Math.max(0, Math.min(MAX, thirstLevel + amount));
    }

    public void drink(int amount, float saturation) {
        thirstLevel = Math.min(MAX, thirstLevel + amount);
        thirstSaturation = Math.min(thirstSaturation + saturation, thirstLevel);
    }

    public int getThirstLevel() { return thirstLevel; }
    public float getThirstSaturation() { return thirstSaturation; }

    public void replaceWith(ThirstManager other) {
        this.thirstLevel = other.thirstLevel;
        this.thirstSaturation = other.thirstSaturation;
        this.starveTickTimer = other.starveTickTimer;
    }

    public NbtCompound toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("thirstLevel", thirstLevel);
        tag.putFloat("thirstSaturation", thirstSaturation);
        tag.putInt("starveTickTimer", starveTickTimer);
        return tag;
    }

    public static ThirstManager fromNbt(NbtCompound tag) {
        ThirstManager t = new ThirstManager();
        t.thirstLevel = tag.getInt("thirstLevel");
        t.thirstSaturation = tag.getFloat("thirstSaturation");
        t.starveTickTimer = tag.getInt("starveTickTimer");
        return t;
    }

    public static ThirstManager get(PlayerEntity player) {
        if (player instanceof PlayerProgressionHolder h) {
            return h.adventuremod$getProgression().thirst;
        }
        return null;
    }
}
