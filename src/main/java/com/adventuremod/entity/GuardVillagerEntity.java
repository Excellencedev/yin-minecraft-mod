package com.adventuremod.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GuardVillagerEntity extends TameableEntity {

    public GuardVillagerEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.1D, 5.0F, 2.0F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerTargetGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, HostileEntity.class, 10, true, false, 
                entity -> !(entity instanceof net.minecraft.entity.mob.CreeperEntity))); // Don't fight creepers to avoid griefing
    }

    public static DefaultAttributeContainer.Builder createGuardAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        return data;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            LivingEntity target = this.getTarget();
            if (target != null && this.squaredDistanceTo(target) < 16.0D) {
                // Raise shield 30% of the time in close combat
                if (this.random.nextFloat() < 0.30F && !this.isBlocking()) {
                    this.setCurrentHand(Hand.OFF_HAND);
                } else if (this.random.nextFloat() < 0.15F && this.isBlocking()) {
                    this.clearActiveItem();
                }
            } else if (this.isBlocking()) {
                this.clearActiveItem();
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        
        if (this.isTamed()) {
            if (this.isOwner(player)) {
                if (itemStack.isOf(Items.BREAD) || itemStack.isOf(Items.COOKED_BEEF) || itemStack.isOf(Items.COOKED_PORKCHOP)) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.heal(6.0F);
                        if (!player.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
                        this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
                        return ActionResult.SUCCESS;
                    }
                }
                
                // Toggle follow/sit
                if (!this.getWorld().isClient) {
                    this.setSitting(!this.isSitting());
                    this.navigation.clearPath();
                    this.setTarget(null);
                }
                return ActionResult.SUCCESS;
            }
        } else {
            if (itemStack.isOf(Items.EMERALD_BLOCK) || itemStack.isOf(Items.GOLDEN_APPLE)) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                if (!this.getWorld().isClient) {
                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.navigation.clearPath();
                        this.setTarget(null);
                        this.getWorld().sendEntityStatus(this, (byte) 7); // Heart particles
                    } else {
                        this.getWorld().sendEntityStatus(this, (byte) 6); // Smoke particles
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        
        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null; // Guards do not reproduce
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }
}
