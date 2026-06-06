package com.adventuremod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class HunterBowItem extends BowItem {
    public HunterBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            ItemStack arrowStack = playerEntity.getProjectileType(stack);
            if (!arrowStack.isEmpty() || playerEntity.getAbilities().creativeMode) {
                if (arrowStack.isEmpty()) {
                    arrowStack = new ItemStack(Items.ARROW);
                }

                int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float pullProgress = (float) useTime / 12.0f;
                pullProgress = (pullProgress * pullProgress + pullProgress * 2.0F) / 3.0F;
                if (pullProgress > 1.0F) {
                    pullProgress = 1.0F;
                }

                if (!((double) pullProgress < 0.1D)) {
                    if (!world.isClient) {
                        ArrowEntity arrowEntity = new ArrowEntity(world, playerEntity, arrowStack, stack);
                        arrowEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 3.75F, 1.0F);
                        if (pullProgress == 1.0F) {
                            arrowEntity.setCritical(true);
                        }

                        stack.damage(1, playerEntity, LivingEntity.getSlotForHand(user.getActiveHand()));
                        world.spawnEntity(arrowEntity);
                    }

                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                            SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);
                    
                    if (!playerEntity.getAbilities().creativeMode) {
                        arrowStack.decrement(1);
                        if (arrowStack.isEmpty()) {
                            playerEntity.getInventory().removeOne(arrowStack);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }
}
