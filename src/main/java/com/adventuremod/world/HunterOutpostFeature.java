package com.adventuremod.world;

import com.adventuremod.entity.ModEntities;
import com.adventuremod.entity.GuardVillagerEntity;
import com.adventuremod.item.ModItems;
import com.adventuremod.villager.ModVillagers;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class HunterOutpostFeature extends Feature<DefaultFeatureConfig> {
    public HunterOutpostFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        BlockPos pos = world.getTopPosition(net.minecraft.world.Heightmap.Type.WORLD_SURFACE_WG, origin);
        
        if (!world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) && !world.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
            return false;
        }

        // Base platform (5x5)
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos floorPos = pos.add(x, -1, z);
                if (world.getRandom().nextFloat() < 0.3F) {
                    world.setBlockState(floorPos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3);
                } else {
                    world.setBlockState(floorPos, Blocks.COBBLESTONE.getDefaultState(), 3);
                }
            }
        }

        // Support Spruce Pillars (height 3)
        for (int y = 0; y < 3; y++) {
            world.setBlockState(pos.add(-2, y, -2), Blocks.SPRUCE_LOG.getDefaultState(), 3);
            world.setBlockState(pos.add(2, y, -2), Blocks.SPRUCE_LOG.getDefaultState(), 3);
            world.setBlockState(pos.add(-2, y, 2), Blocks.SPRUCE_LOG.getDefaultState(), 3);
            world.setBlockState(pos.add(2, y, 2), Blocks.SPRUCE_LOG.getDefaultState(), 3);
        }

        // Roof (5x5 Spruce slabs)
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                world.setBlockState(pos.add(x, 3, z), Blocks.SPRUCE_SLAB.getDefaultState(), 3);
            }
        }

        // Workstation & Accessories
        world.setBlockState(pos.add(-1, 0, -1), ModVillagers.HUNTER_TABLE.getDefaultState(), 3);
        world.setBlockState(pos.add(1, 0, -1), Blocks.LANTERN.getDefaultState(), 3);

        // Loot Chest
        BlockPos chestPos = pos.add(0, 0, 0);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
        if (world.getBlockEntity(chestPos) instanceof ChestBlockEntity chestEntity) {
            chestEntity.setStack(0, new ItemStack(Items.EMERALD, world.getRandom().nextInt(5) + 2));
            chestEntity.setStack(1, new ItemStack(Items.ARROW, world.getRandom().nextInt(12) + 6));
            chestEntity.setStack(2, new ItemStack(ModItems.BOAR_TUSK, world.getRandom().nextInt(2) + 1));
            chestEntity.setStack(3, new ItemStack(ModItems.DEER_ANTLER, world.getRandom().nextInt(2) + 1));
            if (world.getRandom().nextFloat() < 0.25F) {
                chestEntity.setStack(4, new ItemStack(ModItems.BOAR_TUSK_DAGGER, 1));
            }
        }

        // Guard Spawn
        GuardVillagerEntity guard = ModEntities.GUARD_VILLAGER.create(world.toServerWorld());
        if (guard != null) {
            guard.refreshPositionAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 1.5D, 0.0F, 0.0F);
            guard.initialize(world, world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
            world.spawnEntityAndPassengers(guard);
        }

        return true;
    }
}
