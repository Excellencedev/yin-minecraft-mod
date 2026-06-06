package com.adventuremod.mixin;

import com.adventuremod.skills.ModSkills;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getWorld().isClient) return;
        if (source.getAttacker() instanceof PlayerEntity player) {
            ModSkills.onKill(player, self);
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;blockedByShield(Lnet/minecraft/entity/damage/DamageSource;)Z"))
    private void checkParry(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity defender = (LivingEntity) (Object) this;
        if (defender instanceof PlayerEntity player) {
            if (defender.blockedByShield(source)) {
                int useTicks = defender.getItemUseTime();
                if (useTicks <= 8) {
                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1.0F, 1.8F);

                    if (player.getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.CRIT,
                                player.getX(), player.getBodyY(0.5), player.getZ(),
                                15, 0.3, 0.3, 0.3, 0.15);
                    }

                    if (source.getAttacker() instanceof LivingEntity attacker) {
                        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 9), player);
                        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40, 9), player);
                        attacker.takeKnockback(1.0D, player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                    }
                }
            }
        }
    }
}
