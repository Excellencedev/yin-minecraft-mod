package com.adventuremod.ranching;

import com.adventuremod.AdventureMod;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ModRanching {
    public static void registerRanching() {
        AdventureMod.LOGGER.info("Registering Ranching for " + AdventureMod.MOD_ID);

        // Right-click an adult non-baby animal with a bucket: milk.
        UseEntityCallback.EVENT.register((PlayerEntity player, World world, Hand hand, net.minecraft.entity.Entity entity, EntityHitResult hitResult) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (!(entity instanceof AnimalEntity animal) || animal.isBaby()) {
                return ActionResult.PASS;
            }
            if (!stack.isOf(Items.BUCKET)) return ActionResult.PASS;
            // Only milk animals that already implement milking: cow, goat, sheep (mooshroom)
            if (animal instanceof net.minecraft.entity.passive.CowEntity
                    || animal instanceof net.minecraft.entity.passive.GoatEntity
                    || animal instanceof net.minecraft.entity.passive.MooshroomEntity
                    || animal instanceof net.minecraft.entity.passive.SheepEntity) {
                if (world.isClient) return ActionResult.SUCCESS;
                stack.decrement(1);
                ItemStack milk = new ItemStack(Items.MILK_BUCKET);
                if (stack.isEmpty()) player.setStackInHand(hand, milk);
                else player.getInventory().insertStack(milk);
                animal.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }
}
