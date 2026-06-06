package com.adventuremod.survival;

import com.adventuremod.AdventureMod;
import com.adventuremod.progression.PlayerProgressionHolder;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;

public class ModSurvival {
    public static void registerSurvival() {
        AdventureMod.LOGGER.info("Registering Survival for " + AdventureMod.MOD_ID);

        // Drinking water bottles, potions, milk restores thirst.
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient || !(player instanceof PlayerProgressionHolder holder)) {
                return TypedActionResult.pass(player.getStackInHand(hand));
            }
            ThirstManager thirst = holder.adventuremod$getProgression().thirst;
            var stack = player.getStackInHand(hand);

            if (stack.isOf(Items.POTION)) {
                thirst.drink(6, 1.0f);
                if (player instanceof net.minecraft.server.network.ServerPlayerEntity sp) {
                    sp.sendMessage(Text.literal("§b[Thirst] +6 (now " + thirst.getThirstLevel() + "/20)"), true);
                }
            } else if (stack.isOf(Items.MILK_BUCKET)) {
                thirst.drink(8, 2.0f);
                if (player instanceof net.minecraft.server.network.ServerPlayerEntity sp) {
                    sp.sendMessage(Text.literal("§b[Thirst] +8 (now " + thirst.getThirstLevel() + "/20)"), true);
                }
            } else if (stack.isOf(Items.WATER_BUCKET)) {
                thirst.drink(20, 5.0f);
                if (player instanceof net.minecraft.server.network.ServerPlayerEntity sp) {
                    sp.sendMessage(Text.literal("§b[Thirst] fully quenched!"), true);
                }
            }
            return TypedActionResult.pass(stack);
        });
    }
}
