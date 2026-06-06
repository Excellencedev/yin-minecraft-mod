package com.adventuremod.farming;

import com.adventuremod.AdventureMod;
import com.adventuremod.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFarming {
    public static final Block WILD_BERRY_BUSH = Registry.register(
            Registries.BLOCK,
            Identifier.of(AdventureMod.MOD_ID, "wild_berry_bush"),
            new BerryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH))
    );

    public static final Item WILD_BERRY_BUSH_ITEM = Registry.register(
            Registries.ITEM,
            Identifier.of(AdventureMod.MOD_ID, "wild_berry_bush"),
            new BlockItem(WILD_BERRY_BUSH, new Item.Settings())
    );

    public static void registerFarming() {
        AdventureMod.LOGGER.info("Registering Farming for " + AdventureMod.MOD_ID);
    }
}
