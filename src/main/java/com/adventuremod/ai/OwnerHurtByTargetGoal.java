package com.adventuremod.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;

/**
 * Replacement for the 1.20.x AttackWithOwnerGoal.
 * Targets whatever last attacked the tameable's owner.
 */
public class OwnerHurtByTargetGoal extends TrackTargetGoal {
    private final TameableEntity tameable;
    private LivingEntity attacker;
    private int lastHurtTime;

    public OwnerHurtByTargetGoal(TameableEntity tameable) {
        super(tameable, false);
        this.tameable = tameable;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (this.tameable.isTamed() && !this.tameable.isSitting()) {
            LivingEntity owner = this.tameable.getOwner();
            if (owner != null) {
                this.attacker = owner.getAttacker();
                int time = owner.getLastAttackedTime();
                return time != this.lastHurtTime && this.canTrack(this.attacker, net.minecraft.entity.ai.TargetPredicate.DEFAULT);
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.tameable.setTarget(this.attacker);
        LivingEntity owner = this.tameable.getOwner();
        if (owner != null) this.lastHurtTime = owner.getLastAttackedTime();
        super.start();
    }
}
