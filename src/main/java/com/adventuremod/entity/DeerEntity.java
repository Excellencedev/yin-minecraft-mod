package com.adventuremod.entity;

import com.adventuremod.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DeerEntity extends AnimalEntity {
    private static final TrackedData<Boolean> HAS_ANTLERS = DataTracker.registerData(DeerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    
    private int antlerTimer = 6000; // 5 minutes to shed/regrow antler cycle

    public DeerEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new DeerFleeGoal(this, 1.4D));
        this.goalSelector.add(2, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.WHEAT, Items.APPLE), false));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDeerAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32D);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HAS_ANTLERS, true);
    }

    public boolean hasAntlers() {
        return this.dataTracker.get(HAS_ANTLERS);
    }

    public void setHasAntlers(boolean hasAntlers) {
        this.dataTracker.set(HAS_ANTLERS, hasAntlers);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            if (this.antlerTimer > 0) {
                this.antlerTimer--;
            } else {
                this.antlerTimer = 6000; // Reset
                if (this.hasAntlers()) {
                    this.setHasAntlers(false);
                    this.dropItem(ModItems.DEER_ANTLER);
                    this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, 0.8F);
                } else {
                    this.setHasAntlers(true);
                }
            }
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.DEER.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.WHEAT) || stack.isOf(Items.APPLE);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GOAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GOAT_DEATH;
    }

    public static class DeerFleeGoal extends FleeEntityGoal<PlayerEntity> {
        private final DeerEntity deer;

        public DeerFleeGoal(DeerEntity deer, double speed) {
            super(deer, PlayerEntity.class, 12.0F, speed, speed);
            this.deer = deer;
        }

        @Override
        public boolean canStart() {
            PlayerEntity player = this.deer.getWorld().getClosestPlayer(this.deer, 12.0D);
            if (player != null) {
                // If player is sprinting, flee from 12 blocks
                if (player.isSprinting()) {
                    return super.canStart();
                }
                // If player is sneaking or holding tempt items, don't flee
                if (player.isSneaking() || player.isHolding(Items.WHEAT) || player.isHolding(Items.APPLE)) {
                    return false;
                }
                // Otherwise, flee from 6 blocks
                if (this.deer.squaredDistanceTo(player) < 36.0D) {
                    return super.canStart();
                }
            }
            return false;
        }
    }
}
