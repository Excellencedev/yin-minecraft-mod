package com.adventuremod.mythics;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class StagSpiritEntity extends HostileEntity {
    private int teleportCooldown = 0;

    public StagSpiritEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.3D, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, null));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createStagAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 6.0D);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            if (teleportCooldown > 0) teleportCooldown--;

            LivingEntity target = this.getTarget();
            if (target != null && teleportCooldown == 0 && this.squaredDistanceTo(target) > 64.0D) {
                double x = target.getX() + (this.random.nextDouble() - 0.5) * 6.0D;
                double z = target.getZ() + (this.random.nextDouble() - 0.5) * 6.0D;
                double y = target.getY();
                this.requestTeleport(x, y, z);
                teleportCooldown = 60;

                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.END_ROD, x, y, z, 20, 0.5, 0.5, 0.5, 0.1);
                }
            }

            // Maintain Glowing on the target for 5 seconds at a time; refresh if needed
            if (target != null) {
                if (target.hasStatusEffect(StatusEffects.GLOWING)) {
                    // Refresh duration 1 in 80 ticks
                    if (this.random.nextInt(80) == 0) {
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0), this);
                    }
                } else if (this.random.nextFloat() < 0.10F) {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0), this);
                }
            }
        }

        if (this.getWorld().isClient) {
            this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX() + (random.nextDouble() - 0.5), this.getY() + random.nextDouble(), this.getZ() + (random.nextDouble() - 0.5), 0, 0, 0);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity attacker && this.squaredDistanceTo(attacker) < 9.0D) {
            attacker.takeKnockback(0.5D, this.getX() - attacker.getX(), this.getZ() - attacker.getZ());
        }
        return super.damage(source, amount);
    }
}
