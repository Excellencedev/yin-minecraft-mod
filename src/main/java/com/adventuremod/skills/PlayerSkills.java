package com.adventuremod.skills;

import com.adventuremod.progression.PlayerProgressionHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerSkills {
    private int huntingLevel = 1;
    private int combatLevel = 1;
    private int farmingLevel = 1;
    private int totalHuntingXp = 0;
    private int totalCombatXp = 0;
    private int totalFarmingXp = 0;

    public void addHuntingXp(int amount) {
        addHuntingXp(amount, null);
    }

    public void addHuntingXp(int amount, PlayerEntity cause) {
        if (amount <= 0) return;
        totalHuntingXp += amount;
        int newLevel = calculateLevel(totalHuntingXp);
        if (newLevel > huntingLevel && cause instanceof ServerPlayerEntity sp) {
            sp.sendMessage(Text.literal("§a[Skill] Hunting leveled up: " + (newLevel - 1) + " -> " + newLevel), true);
        }
        huntingLevel = newLevel;
    }

    public void addCombatXp(int amount) {
        addCombatXp(amount, null);
    }

    public void addCombatXp(int amount, PlayerEntity cause) {
        if (amount <= 0) return;
        totalCombatXp += amount;
        int newLevel = calculateLevel(totalCombatXp);
        if (newLevel > combatLevel && cause instanceof ServerPlayerEntity sp) {
            sp.sendMessage(Text.literal("§c[Skill] Combat leveled up: " + (newLevel - 1) + " -> " + newLevel), true);
        }
        combatLevel = newLevel;
    }

    public void addFarmingXp(int amount) {
        addFarmingXp(amount, null);
    }

    public void addFarmingXp(int amount, PlayerEntity cause) {
        if (amount <= 0) return;
        totalFarmingXp += amount;
        int newLevel = calculateLevel(totalFarmingXp);
        if (newLevel > farmingLevel && cause instanceof ServerPlayerEntity sp) {
            sp.sendMessage(Text.literal("§e[Skill] Farming leveled up: " + (newLevel - 1) + " -> " + newLevel), true);
        }
        farmingLevel = newLevel;
    }

    private int calculateLevel(int totalXp) {
        return Math.min(50, (int) (Math.sqrt(totalXp / 50.0)) + 1);
    }

    public int getHuntingLevel() { return huntingLevel; }
    public int getCombatLevel() { return combatLevel; }
    public int getFarmingLevel() { return farmingLevel; }
    public int getTotalHuntingXp() { return totalHuntingXp; }
    public int getTotalCombatXp() { return totalCombatXp; }
    public int getTotalFarmingXp() { return totalFarmingXp; }

    public void replaceWith(PlayerSkills other) {
        this.huntingLevel = other.huntingLevel;
        this.combatLevel = other.combatLevel;
        this.farmingLevel = other.farmingLevel;
        this.totalHuntingXp = other.totalHuntingXp;
        this.totalCombatXp = other.totalCombatXp;
        this.totalFarmingXp = other.totalFarmingXp;
    }

    public NbtCompound toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("huntingLevel", huntingLevel);
        tag.putInt("combatLevel", combatLevel);
        tag.putInt("farmingLevel", farmingLevel);
        tag.putInt("totalHuntingXp", totalHuntingXp);
        tag.putInt("totalCombatXp", totalCombatXp);
        tag.putInt("totalFarmingXp", totalFarmingXp);
        return tag;
    }

    public static PlayerSkills fromNbt(NbtCompound tag) {
        PlayerSkills skills = new PlayerSkills();
        skills.huntingLevel = Math.max(1, tag.getInt("huntingLevel"));
        skills.combatLevel = Math.max(1, tag.getInt("combatLevel"));
        skills.farmingLevel = Math.max(1, tag.getInt("farmingLevel"));
        skills.totalHuntingXp = tag.getInt("totalHuntingXp");
        skills.totalCombatXp = tag.getInt("totalCombatXp");
        skills.totalFarmingXp = tag.getInt("totalFarmingXp");
        return skills;
    }

    public void addXp(String skill, int amount) {
        switch (skill) {
            case "hunting" -> addHuntingXp(amount);
            case "combat" -> addCombatXp(amount);
            case "farming" -> addFarmingXp(amount);
        }
    }

    // ---- Static helpers (used by gameplay code that does not hold a PlayerProgression ref) ----

    public static void addHuntingXp(PlayerEntity player, int amount) {
        if (player instanceof PlayerProgressionHolder h) {
            h.adventuremod$getProgression().skills.addHuntingXp(amount, player);
        }
    }

    public static void addCombatXp(PlayerEntity player, int amount) {
        if (player instanceof PlayerProgressionHolder h) {
            h.adventuremod$getProgression().skills.addCombatXp(amount, player);
        }
    }

    public static void addFarmingXp(PlayerEntity player, int amount) {
        if (player instanceof PlayerProgressionHolder h) {
            h.adventuremod$getProgression().skills.addFarmingXp(amount, player);
        }
    }
}
