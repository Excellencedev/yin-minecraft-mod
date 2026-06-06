package com.adventuremod.mount;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RideableBoarEntity extends AnimalEntity implements Saddleable {
    private boolean saddled = false;

    public RideableBoarEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(2, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.CARROT, Items.POTATO), false));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createMountAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0D);
    }

    @Override
    public boolean canBeSaddled() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void saddle(ItemStack stack, @Nullable SoundCategory soundCategory) {
        this.saddled = true;
        if (soundCategory != null) {
            this.playSound(SoundEvents.ENTITY_PIG_SADDLE, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddled() {
        return this.saddled;
    }

    @Override
    public SoundEvent getSaddleSound() {
        return SoundEvents.ENTITY_PIG_SADDLE;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.SADDLE) && this.canBeSaddled() && !this.isSaddled()) {
            if (!player.getAbilities().creativeMode) stack.decrement(1);
            this.saddle(stack, SoundCategory.PLAYERS);
            return ActionResult.SUCCESS;
        }
        if (this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.getWorld().isClient) {
                player.startRiding(this);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void travel(net.minecraft.util.math.Vec3d movementInput) {
        if (this.isSaddled() && this.hasPassengers() && this.getFirstPassenger() instanceof PlayerEntity player) {
            // Snap the boar to face the player's yaw so steering feels natural.
            this.setYaw(player.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(player.getPitch() * 0.5F);
            this.bodyYaw = this.getYaw();
            this.headYaw = this.bodyYaw;
        }
        super.travel(movementInput);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CARROT) || stack.isOf(Items.POTATO);
    }
}
