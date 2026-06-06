package com.adventuremod.skills;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.DeerEntity;
import com.adventuremod.entity.WildBoarEntity;
import com.adventuremod.mythics.StagSpiritEntity;
import com.adventuremod.bosses.ForestGuardianBossEntity;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ModSkills {
    public static void registerSkills() {
        AdventureMod.LOGGER.info("Registering Skills for " + AdventureMod.MOD_ID);

        // Farming XP: break mature crops
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!world.isClient && state.getBlock() instanceof CropBlock crop && crop.isMature(state)) {
                PlayerSkills.addFarmingXp(player, 4);
            }
            return true;
        });
    }

    /**
     * Called by mixin when a player kills a living entity.
     * Awards combat XP for mobs, hunting XP for game animals.
     */
    public static void onKill(PlayerEntity player, LivingEntity victim) {
        if (player.getWorld().isClient) return;

        if (victim instanceof ForestGuardianBossEntity) {
            PlayerSkills.addCombatXp(player, 200);
            PlayerSkills.addHuntingXp(player, 200);
            return;
        }
        if (victim instanceof StagSpiritEntity) {
            PlayerSkills.addCombatXp(player, 80);
            PlayerSkills.addHuntingXp(player, 80);
            return;
        }
        if (victim instanceof WildBoarEntity || victim instanceof DeerEntity) {
            PlayerSkills.addHuntingXp(player, 30);
        }
        if (victim instanceof net.minecraft.entity.mob.Monster) {
            PlayerSkills.addCombatXp(player, 10);
        }
    }
}
