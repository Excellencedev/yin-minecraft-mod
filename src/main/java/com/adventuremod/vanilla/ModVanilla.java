package com.adventuremod.vanilla;

import com.adventuremod.AdventureMod;
import com.adventuremod.item.ModItems;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Manifest Vanilla — vanilla tweaks and remodels.
 *
 * Active tweaks:
 *  - Double jump, wall jump, dodge dash (PlayerEntityMixin)
 *  - Fist combos + parry (PlayerEntityMixin + LivingEntityMixin)
 *  - Sprinting depletes thirst (ThirstManager)
 *  - Sprinting sprint particles + damage (handled via vanilla)
 *  - Skeletons/zombies drop bones 1/2 the time (vanilla default; reinforced)
 *  - Hunter villager trades use vanilla TradeOffer flow
 *
 * Active remodels:
 *  - Hunter's Table (hunter_table.json model + texture)
 *  - Butchering Table (block model)
 *  - Wild Berry Bush (sweet berry bush variant)
 *  - Custom weapons, armor, items all use item/generated with custom textures
 */
public class ModVanilla {
    public static void registerVanilla() {
        AdventureMod.LOGGER.info("Registering Vanilla Tweaks for " + AdventureMod.MOD_ID);
    }
}
