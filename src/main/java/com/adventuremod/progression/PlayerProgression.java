package com.adventuremod.progression;

import com.adventuremod.rpg.PlayerClass;
import com.adventuremod.skills.PlayerSkills;
import com.adventuremod.survival.ThirstManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

/**
 * Per-player progression container that wraps skills, thirst, and class.
 * Persists through player death and respawning.
 */
public class PlayerProgression {
    public final PlayerSkills skills = new PlayerSkills();
    public final ThirstManager thirst = new ThirstManager();
    public PlayerClass playerClass = PlayerClass.NONE;

    public NbtCompound toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.put("Skills", skills.toNbt());
        tag.put("Thirst", thirst.toNbt());
        tag.putString("Class", playerClass.getName());
        return tag;
    }

    public static PlayerProgression fromNbt(NbtCompound tag) {
        PlayerProgression p = new PlayerProgression();
        if (tag.contains("Skills")) {
            // skills already set; copy values
            NbtCompound s = tag.getCompound("Skills");
            // Re-attach by mutating fields through addXp with a noop or use a setter
            // For simplicity we read into a new PlayerSkills and replace
            PlayerSkills loaded = PlayerSkills.fromNbt(s);
            p.skills.replaceWith(loaded);
        }
        if (tag.contains("Thirst")) {
            ThirstManager loaded = ThirstManager.fromNbt(tag.getCompound("Thirst"));
            p.thirst.replaceWith(loaded);
        }
        if (tag.contains("Class")) {
            p.playerClass = PlayerClass.fromName(tag.getString("Class"));
        }
        return p;
    }
}
