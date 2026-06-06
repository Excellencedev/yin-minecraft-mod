package com.adventuremod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;

public class AntlerGreatswordItem extends SwordItem {
    public AntlerGreatswordItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        DamageSource damageSource = attacker.getDamageSources().playerAttack(attacker instanceof net.minecraft.entity.player.PlayerEntity player ? player : null);
        Box sweepBox = target.getBoundingBox().expand(3.0D, 1.0D, 3.0D);
        List<LivingEntity> targets = target.getWorld().getEntitiesByClass(LivingEntity.class, sweepBox, 
                entity -> entity != attacker && entity != target && (entity instanceof Monster || entity instanceof LivingEntity));

        for (LivingEntity nearbyTarget : targets) {
            nearbyTarget.damage(damageSource, 3.0f);
            double dx = nearbyTarget.getX() - attacker.getX();
            double dz = nearbyTarget.getZ() - attacker.getZ();
            double length = Math.sqrt(dx * dx + dz * dz);
            if (length > 0) {
                nearbyTarget.takeKnockback(1.2D, -dx / length, -dz / length);
            }
        }

        if (attacker.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK, 
                    target.getX(), target.getBodyY(0.5), target.getZ(), 
                    5, 0.5, 0.2, 0.5, 0.0);
        }

        return super.postHit(stack, target, attacker);
    }
}
