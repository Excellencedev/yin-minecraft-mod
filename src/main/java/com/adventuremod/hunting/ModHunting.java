package com.adventuremod.hunting;

import com.adventuremod.AdventureMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModHunting {
    public static final Block BUTCHERING_TABLE = Registry.register(
            Registries.BLOCK,
            Identifier.of(AdventureMod.MOD_ID, "butchering_table"),
            new ButcheringTableBlock(AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE))
    );

    public static final Item BUTCHERING_TABLE_ITEM = Registry.register(
            Registries.ITEM,
            Identifier.of(AdventureMod.MOD_ID, "butchering_table"),
            new BlockItem(BUTCHERING_TABLE, new Item.Settings())
    );

    public static void registerHunting() {
        AdventureMod.LOGGER.info("Registering Hunting for " + AdventureMod.MOD_ID);
    }
}
