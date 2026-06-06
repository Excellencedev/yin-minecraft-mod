package com.adventuremod.hunting;

import com.adventuremod.item.ModItems;
import com.adventuremod.skills.PlayerSkills;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ButcheringTableBlock extends Block {
    private static final Map<Item, Item> RAW_TO_BUTCHERED = new HashMap<>();
    static {
        // All raw meats yield boar meat in this mod's theme
        RAW_TO_BUTCHERED.put(Items.PORKCHOP, ModItems.RAW_BOAR_MEAT);
        RAW_TO_BUTCHERED.put(Items.BEEF, ModItems.RAW_BOAR_MEAT);
        RAW_TO_BUTCHERED.put(Items.CHICKEN, ModItems.RAW_BOAR_MEAT);
        RAW_TO_BUTCHERED.put(Items.MUTTON, ModItems.RAW_BOAR_MEAT);
        RAW_TO_BUTCHERED.put(Items.RABBIT, ModItems.RAW_BOAR_MEAT);
    }

    public ButcheringTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();
        Item butchered = RAW_TO_BUTCHERED.get(stack.getItem());
        if (butchered == null) {
            return ActionResult.PASS;
        }

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        int count = stack.getCount();
        // Each raw meat produces 1 butchered meat + a 50% chance of a bone
        stack.decrement(count);
        player.getInventory().offerOrDrop(new ItemStack(butchered, count));
        int boneCount = (count + 1) / 2; // 1 bone per 2 raw meats, rounded up
        player.getInventory().offerOrDrop(new ItemStack(Items.BONE, boneCount));

        // Award hunting XP
        PlayerSkills.addHuntingXp(player, count * 5);

        world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return ActionResult.SUCCESS;
    }
}
