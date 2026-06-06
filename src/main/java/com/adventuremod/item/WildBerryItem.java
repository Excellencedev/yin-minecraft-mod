package com.adventuremod.item;

import com.adventuremod.survival.ThirstManager;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WildBerryItem extends Item {
    public WildBerryItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        if (!world.isClient && user instanceof PlayerEntity player) {
            ThirstManager tm = ThirstManager.get(player);
            if (tm != null) tm.drink(2, 0.5f);
        }
        return result;
    }
}
