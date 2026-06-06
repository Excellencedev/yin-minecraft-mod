package com.adventuremod.villager;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.ModEntities;
import com.adventuremod.item.ModItems;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Optional;

public class ModVillagers {
    public static final Block HUNTER_TABLE = Registry.register(
            Registries.BLOCK,
            Identifier.of(AdventureMod.MOD_ID, "hunter_table"),
            new Block(AbstractBlock.Settings.copy(Blocks.FLETCHING_TABLE))
    );

    public static final Item HUNTER_TABLE_ITEM = Registry.register(
            Registries.ITEM,
            Identifier.of(AdventureMod.MOD_ID, "hunter_table"),
            new BlockItem(HUNTER_TABLE, new Item.Settings())
    );

    public static final RegistryKey<PointOfInterestType> HUNTER_POI_KEY = RegistryKey.of(
            Registries.POINT_OF_INTEREST_TYPE.getKey(),
            Identifier.of(AdventureMod.MOD_ID, "hunter_poi")
    );

    public static final PointOfInterestType HUNTER_POI = PointOfInterestHelper.register(
            Identifier.of(AdventureMod.MOD_ID, "hunter_poi"),
            1, 1, HUNTER_TABLE
    );

    public static final VillagerProfession HUNTER_PROFESSION = Registry.register(
            Registries.VILLAGER_PROFESSION,
            Identifier.of(AdventureMod.MOD_ID, "hunter"),
            new VillagerProfession(
                    "hunter",
                    entry -> entry.matchesKey(HUNTER_POI_KEY),
                    entry -> entry.matchesKey(HUNTER_POI_KEY),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER
            )
    );

    public static void registerVillagers() {
        AdventureMod.LOGGER.info("Registering Villager Professions for " + AdventureMod.MOD_ID);
        registerTrades();
    }

    private static void registerTrades() {
        // Level 1: Meat for Emeralds; sells arrows
        TradeOfferHelper.registerVillagerOffers(HUNTER_PROFESSION, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.RAW_BOAR_MEAT, 4),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.RAW_VENISON, 4),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 1),
                    new ItemStack(Items.ARROW, 8),
                    12, 1, 0.05F
            ));
        });

        // Level 2: Tusks/Antlers for Emeralds; sells armor
        TradeOfferHelper.registerVillagerOffers(HUNTER_PROFESSION, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.BOAR_TUSK, 2),
                    new ItemStack(Items.EMERALD, 3),
                    12, 5, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.DEER_ANTLER, 2),
                    new ItemStack(Items.EMERALD, 3),
                    12, 5, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 4),
                    new ItemStack(Items.LEATHER_CHESTPLATE, 1),
                    8, 4, 0.05F
            ));
        });

        // Level 3: Custom Weapons for Emeralds
        TradeOfferHelper.registerVillagerOffers(HUNTER_PROFESSION, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 8),
                    new ItemStack(ModItems.BOAR_TUSK_DAGGER, 1),
                    3, 10, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 12),
                    new ItemStack(ModItems.ANTLER_GREATSWORD, 1),
                    3, 15, 0.05F
            ));
        });

        // Level 4: Hunter Bows and Tipped Arrows
        TradeOfferHelper.registerVillagerOffers(HUNTER_PROFESSION, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 16),
                    new ItemStack(ModItems.HUNTER_BOW, 1),
                    3, 20, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 4),
                    new ItemStack(Items.SPECTRAL_ARROW, 8),
                    12, 10, 0.05F
            ));
        });
    }
}
