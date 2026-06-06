package com.adventuremod.mount;

import com.adventuremod.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SaddledComponent;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RideableBoarEntity extends AnimalEntity implements Saddleable {
    private final SaddledComponent saddledComponent;

    public RideableBoarEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this, this.dataTracker, 8, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(2, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.CARROT, Items.POTATO), false));
        this.goalSelector.add(3, new BreedGoal(this, 1.0D));
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
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void saddle(@Nullable ItemStack stack) {
        this.saddledComponent.setSaddled(true);
    }

    @Override
    public boolean isSaddled() {
        return this.saddledComponent.isSaddled();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.SADDLE) && this.isSaddleable() && !this.isSaddled()) {
            if (!player.getAbilities().creativeMode) stack.decrement(1);
            this.saddle(stack);
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
    public void travel(net.minecraft.entity.MovementInput movementInput) {
        if (this.isSaddled() && this.hasPassenger()) {
            if (this.getFirstPassenger() instanceof PlayerEntity player) {
                this.setYaw(player.getYaw());
                this.prevYaw = this.getYaw();
                this.setPitch(player.getPitch() * 0.5F);
                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float speed = (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                super.travel(new net.minecraft.entity.MovementInput(
                        player.getMovementInput().forward(),
                        player.getMovementInput().sideways(),
                        false, false
                ));
            }
        } else {
            super.travel(movementInput);
        }
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
