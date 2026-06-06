package com.adventuremod.entity;

import com.adventuremod.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
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
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WildBoarEntity extends AnimalEntity implements Angerable {
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime;
    @Nullable
    private UUID angryAt;
    private int chargeCooldown = 0;

    public WildBoarEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.add(2, new EatCropsGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.CARROT, Items.POTATO), false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
    }

    public static DefaultAttributeContainer.Builder createBoarAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5D);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
            if (this.chargeCooldown > 0) {
                this.chargeCooldown--;
            }

            LivingEntity target = this.getTarget();
            if (target != null && this.chargeCooldown == 0 && this.squaredDistanceTo(target) > 16.0D && this.squaredDistanceTo(target) < 64.0D) {
                this.performCharge(target);
            }
        }
    }

    private void performCharge(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        if (distance > 0) {
            this.setVelocity(dx / distance * 0.8D, 0.25D, dz / distance * 0.8D);
            this.playSound(SoundEvents.ENTITY_PIG_AMBIENT, 1.5F, 0.8F);
            this.chargeCooldown = 100; // 5 second cooldown
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.WILD_BOAR.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CARROT) || stack.isOf(Items.POTATO);
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    public static class EatCropsGoal extends MoveToTargetPosGoal {
        private final WildBoarEntity boar;

        public EatCropsGoal(WildBoarEntity boar, double speed) {
            super(boar, speed, 8);
            this.boar = boar;
        }

        @Override
        public boolean canStart() {
            if (this.boar.isBaby()) return false;
            return super.canStart();
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.WHEAT) || state.isOf(Blocks.CARROTS) || state.isOf(Blocks.POTATOES)) {
                if (state.getBlock() instanceof CropBlock cropBlock) {
                    return cropBlock.isMature(state);
                }
            }
            return false;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.hasReached()) {
                World world = this.boar.getWorld();
                BlockPos pos = this.getTargetPos();
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof CropBlock) {
                    world.breakBlock(pos, false);
                    this.boar.heal(4.0F);
                    this.boar.playSound(SoundEvents.ENTITY_PIG_AMBIENT, 1.0F, 0.7F);
                    this.stop();
                }
            }
        }
    }
}
