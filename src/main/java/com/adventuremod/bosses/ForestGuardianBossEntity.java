package com.adventuremod.bosses;

import com.adventuremod.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class ForestGuardianBossEntity extends HostileEntity {
    private final ServerBossBar bossBar;
    private int phase = 1;
    private int attackCooldown = 0;

    public ForestGuardianBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = new ServerBossBar(
                Text.literal("Forest Guardian"),
                BossBar.Color.GREEN,
                BossBar.Style.PROGRESS
        );
        this.setPersistent();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 0, true, false, null));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createBossAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

            if (this.getHealth() < this.getMaxHealth() * 0.5F && phase == 1) {
                phase = 2;
                this.playSound(SoundEvents.ENTITY_WITHER_SPAWN, 1.0F, 1.0F);
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 1, this.getZ(), 30, 1, 1, 1, 0.1);
                }
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(18.0D);
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.36D);
            }

            if (attackCooldown > 0) attackCooldown--;

            LivingEntity target = this.getTarget();
            if (target != null && attackCooldown == 0) {
                if (phase == 2 && this.squaredDistanceTo(target) < 100.0D) {
                    double dx = target.getX() - this.getX();
                    double dz = target.getZ() - this.getZ();
                    double dist = Math.sqrt(dx * dx + dz * dz);
                    if (dist > 0) {
                        this.setVelocity(dx / dist * 1.5D, 0.3D, dz / dist * 1.5D);
                        this.velocityChanged = true;
                    }
                    attackCooldown = 60;
                } else if (this.squaredDistanceTo(target) < 16.0D) {
                    attackCooldown = 20;
                }

                // Phase 2: AoE ground slam every 5 seconds
                if (phase == 2 && this.age % 100 == 0) {
                    Box slam = this.getBoundingBox().expand(6.0D, 2.0D, 6.0D);
                    List<LivingEntity> near = this.getWorld().getEntitiesByClass(LivingEntity.class, slam, e -> e != this && e.isAlive());
                    for (LivingEntity e : near) {
                        e.damage(this.getDamageSources().mobAttack(this), 8.0F);
                        e.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1), this);
                        e.takeKnockback(1.0D, this.getX() - e.getX(), this.getZ() - e.getZ());
                    }
                    this.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 1.5F, 0.7F);
                    if (this.getWorld() instanceof ServerWorld sw) {
                        sw.spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 8, 3, 0.5, 3, 0.1);
                    }
                }
            }
        } else {
            if (this.random.nextFloat() < 0.1F) {
                this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
                        this.getX() + (random.nextDouble() - 0.5) * 2,
                        this.getY() + random.nextDouble() * 2,
                        this.getZ() + (random.nextDouble() - 0.5) * 2,
                        0, 0, 0);
            }
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier) {
        super.dropEquipment(source, lootingMultiplier);
        this.dropItem(ModItems.ANTLER_GREATSWORD);
        this.dropItem(net.minecraft.item.Items.EMERALD_BLOCK, 3 + this.random.nextInt(3));
    }

    @Override
    protected void dropXp() {
        // Manually drop 100 XP as orbs since dropXp(int) doesn't exist in 1.21.1 yarn
        if (this.getWorld() instanceof net.minecraft.server.world.ServerWorld sw) {
            net.minecraft.entity.ExperienceOrbEntity.spawn(sw, this.getPos(), 100);
        }
    }
}
