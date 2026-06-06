package com.adventuremod.mixin;

import com.adventuremod.movement.DashablePlayer;
import com.adventuremod.progression.PlayerProgression;
import com.adventuremod.progression.PlayerProgressionHolder;
import com.adventuremod.rpg.PlayerClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DashablePlayer, PlayerProgressionHolder {
    @Unique
    private int adventuremod$jumpCount = 0;
    @Unique
    private int adventuremod$dashCooldown = 0;
    @Unique
    private int adventuremod$dashInvulTicks = 0;
    @Unique
    private int adventuremod$comboCount = 0;
    @Unique
    private int adventuremod$lastAttackTick = 0;
    @Unique
    private int adventuremod$wallJumpCooldown = 0;
    @Unique
    private final PlayerProgression adventuremod$progression = new PlayerProgression();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public PlayerProgression adventuremod$getProgression() {
        return this.adventuremod$progression;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        // Thirst
        this.adventuremod$progression.thirst.tick((PlayerEntity) (Object) this);

        // Movement timers
        if (this.adventuremod$dashInvulTicks > 0) this.adventuremod$dashInvulTicks--;
        if (this.adventuremod$dashCooldown > 0) this.adventuremod$dashCooldown--;
        if (this.adventuremod$wallJumpCooldown > 0) this.adventuremod$wallJumpCooldown--;
        if (this.isOnGround()) this.adventuremod$jumpCount = 0;
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Wall jump: midair, colliding with a wall horizontally
        if (!player.isOnGround() && player.horizontalCollision && this.adventuremod$wallJumpCooldown == 0) {
            double yawRad = Math.toRadians(player.getYaw());
            double dx = -Math.sin(yawRad) * 0.45;
            double dz = Math.cos(yawRad) * 0.45;
            // Push the player back away from the wall
            player.setVelocity(-dx, 0.45, -dz);

            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.PLAYERS, 0.8F, 1.2F);

            if (player.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 8, 0.1, 0.1, 0.1, 0.05);
            }
            this.adventuremod$wallJumpCooldown = 10;
            ci.cancel();
            return;
        }

        // Double jump: midair, no more than 1 extra jump consumed
        if (!player.isOnGround() && !player.horizontalCollision && this.adventuremod$jumpCount < 1) {
            this.adventuremod$jumpCount++;
            Vec3d velocity = player.getVelocity();
            player.setVelocity(velocity.x, 0.42, velocity.z);

            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1.0F, 1.5F);

            if (player.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.1, player.getZ(), 6, 0.2, 0.1, 0.2, 0.02);
            }
            ci.cancel();
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.adventuremod$dashInvulTicks > 0) {
            // Allow void and creative kills to bypass dash i-frames
            String name = source.getName();
            if (!"outOfWorld".equals(name) && !"genericKill".equals(name)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Override
    public void adventuremod$performDash() {
        if (this.adventuremod$dashCooldown == 0) {
            this.adventuremod$dashCooldown = 30; // 1.5 second cooldown
            this.adventuremod$dashInvulTicks = 10; // 0.5 second of invulnerability

            double yawRad = Math.toRadians(this.getYaw());
            double speed = 1.8D;
            double newVx = -Math.sin(yawRad) * speed;
            double newVz = Math.cos(yawRad) * speed;
            this.setVelocity(newVx, 0.15D, newVz);
            this.velocityChanged = true;

            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.2F, 1.2F);

            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.POOF, this.getX(), this.getY() + 0.5, this.getZ(), 12, 0.3, 0.2, 0.3, 0.02);
            }
        }
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getMainHandStack().isEmpty()) {
            int currentTick = player.age;
            if (currentTick - this.adventuremod$lastAttackTick > 30) {
                this.adventuremod$comboCount = 0;
            } else {
                this.adventuremod$comboCount = (this.adventuremod$comboCount + 1) % 3;
            }
            this.adventuremod$lastAttackTick = currentTick;

            if (this.adventuremod$comboCount == 1 && target instanceof LivingEntity livingTarget) {
                double yawRad = Math.toRadians(player.getYaw());
                livingTarget.takeKnockback(0.6F, Math.sin(yawRad), -Math.cos(yawRad));
            } else if (this.adventuremod$comboCount == 2 && target instanceof LivingEntity livingTarget) {
                double yawRad = Math.toRadians(player.getYaw());
                livingTarget.takeKnockback(1.4F, Math.sin(yawRad), -Math.cos(yawRad));

                if (player.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK,
                            target.getX(), target.getBodyY(0.5), target.getZ(),
                            3, 0.2, 0.2, 0.2, 0.0);
                }
            }
        }
    }

    @Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
    private void onGetAttributeValue(net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.attribute.EntityAttribute> attribute, CallbackInfoReturnable<Double> cir) {
        PlayerClass pc = this.adventuremod$progression.playerClass;
        if (attribute.value() == net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED) {
            if (pc != PlayerClass.NONE) {
                cir.setReturnValue(cir.getReturnValue() * pc.speedMultiplier);
            }
            return;
        }
        if (attribute.value() == net.minecraft.entity.attribute.EntityAttributes.GENERIC_ATTACK_DAMAGE) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            if (player.getMainHandStack().isEmpty()) {
                // Fist combo: punch 1 = 4 damage, punch 3 = 6 damage, then class multiplier
                double baseFist = 4.0D;
                if (this.adventuremod$comboCount == 2) baseFist = 6.0D;
                cir.setReturnValue(baseFist * pc.combatMultiplier);
            } else if (pc != PlayerClass.NONE) {
                cir.setReturnValue(cir.getReturnValue() * pc.combatMultiplier);
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeProgressionNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("AdventureModProgression", this.adventuremod$progression.toNbt());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readProgressionNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("AdventureModProgression")) {
            PlayerProgression loaded = PlayerProgression.fromNbt(nbt.getCompound("AdventureModProgression"));
            // Re-assign fields since final can't be replaced
            this.adventuremod$progression.skills.replaceWith(loaded.skills);
            this.adventuremod$progression.thirst.replaceWith(loaded.thirst);
            this.adventuremod$progression.playerClass = loaded.playerClass;
        }
    }
}
