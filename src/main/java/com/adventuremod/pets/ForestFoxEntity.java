package com.adventuremod.pets;

import com.adventuremod.ai.TrackOwnerTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForestFoxEntity extends TameableEntity {
    public ForestFoxEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.1D, 5.0F, 2.0F));
        this.goalSelector.add(4, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.SWEET_BERRIES, Items.GLOW_BERRIES), false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerTargetGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, HostileEntity.class, 10, true, false, null));
    }

    public static DefaultAttributeContainer.Builder createFoxAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.SWEET_BERRIES) || stack.isOf(Items.GLOW_BERRIES)) {
            if (!this.isTamed()) {
                if (!player.getAbilities().creativeMode) stack.decrement(1);
                if (!this.getWorld().isClient) {
                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.getWorld().sendEntityStatus(this, (byte) 7);
                    } else {
                        this.getWorld().sendEntityStatus(this, (byte) 6);
                    }
                }
                return ActionResult.SUCCESS;
            }
            if (this.getHealth() < this.getMaxHealth()) {
                this.heal(4.0F);
                if (!player.getAbilities().creativeMode) stack.decrement(1);
                this.playSound(SoundEvents.ENTITY_FOX_EAT, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
        }
        if (this.isTamed() && this.isOwner(player)) {
            if (!this.getWorld().isClient) {
                this.setSitting(!this.isSitting());
                this.navigation.stop();
                this.setTarget(null);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false; // Foxes are tamed, not bred
    }
}
