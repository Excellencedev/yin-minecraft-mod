package com.adventuremod.survival;

import com.adventuremod.progression.PlayerProgressionHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class ThirstManager {
    public static final int MAX = 20;
    private int thirstLevel = MAX;
    private float thirstSaturation = 5.0f;
    private int thirstTickTimer = 0;

    public void tick(PlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) return;

        thirstTickTimer++;
        if (thirstTickTimer >= 200) {
            thirstTickTimer = 0;
            if (player.isSprinting()) {
                addThirst(-1);
            }
        }

        if (thirstLevel <= 0) {
            player.damage(player.getDamageSources().starve(), 1.0f);
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
    }

    public NbtCompound toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("thirstLevel", thirstLevel);
        tag.putFloat("thirstSaturation", thirstSaturation);
        return tag;
    }

    public static ThirstManager fromNbt(NbtCompound tag) {
        ThirstManager t = new ThirstManager();
        t.thirstLevel = tag.getInt("thirstLevel");
        t.thirstSaturation = tag.getFloat("thirstSaturation");
        return t;
    }

    public static ThirstManager get(PlayerEntity player) {
        if (player instanceof PlayerProgressionHolder h) {
            return h.adventuremod$getProgression().thirst;
        }
        return null;
    }
}
