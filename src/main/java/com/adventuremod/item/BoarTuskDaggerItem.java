package com.adventuremod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import org.joml.Vector3f;

public class BoarTuskDaggerItem extends SwordItem {
    public BoarTuskDaggerItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 1), attacker);
        
        if (target.getWorld() instanceof ServerWorld serverWorld) {
            DustParticleEffect bleedParticle = new DustParticleEffect(new Vector3f(0.6f, 0.0f, 0.0f), 1.0f);
            serverWorld.spawnParticles(bleedParticle, 
                    target.getX(), target.getBodyY(0.5), target.getZ(), 
                    15, 0.2, 0.4, 0.2, 0.05);
        }
        
        return super.postHit(stack, target, attacker);
    }
}
