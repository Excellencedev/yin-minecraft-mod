package com.adventuremod.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;

/**
 * Replacement for the 1.20.x TrackOwnerTargetGoal that is no longer public in 1.21.1 yarn.
 * Sets this tameable's target to whatever its owner is currently attacking.
 */
public class TrackOwnerTargetGoal extends TrackTargetGoal {
    private final TameableEntity tameable;
    private LivingEntity defending;
    private int lastAttackedTime;

    public TrackOwnerTargetGoal(TameableEntity tameable) {
        super(tameable, false);
        this.tameable = tameable;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (!this.tameable.isTamed() || this.tameable.isSitting()) return false;
        LivingEntity owner = this.tameable.getOwner();
        if (owner == null) return false;
        this.defending = owner.getAttacking();
        int time = owner.getLastAttackedTime();
        return time != this.lastAttackedTime && this.canTrack(this.defending, TargetPredicate.DEFAULT);
    }

    @Override
    public void start() {
        this.tameable.setTarget(this.defending);
        LivingEntity owner = this.tameable.getOwner();
        if (owner != null) this.lastAttackedTime = owner.getLastAttackedTime();
        super.start();
    }
}
