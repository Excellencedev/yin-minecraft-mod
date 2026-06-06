package com.adventuremod.item;

import com.adventuremod.progression.PlayerProgressionHolder;
import com.adventuremod.rpg.PlayerClass;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ClassTomeItem extends Item {
    public ClassTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof PlayerProgressionHolder holder) {
            if (!world.isClient) {
                PlayerClass current = holder.adventuremod$getProgression().playerClass;
                user.sendMessage(Text.literal("§6Current class: " + current.getName()), false);
                user.sendMessage(Text.literal("§eUse §f/class <hunter|warrior|scout|none>§e to choose."), false);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
