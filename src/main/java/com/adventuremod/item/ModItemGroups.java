package com.adventuremod.item;

import com.adventuremod.armor.ModArmors;
import com.adventuremod.farming.ModFarming;
import com.adventuremod.hunting.ModHunting;
import com.adventuremod.villager.ModVillagers;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModItemGroups {
    private static final RegistryKey<ItemGroup> INGREDIENTS = vanillaGroup("ingredients");
    private static final RegistryKey<ItemGroup> FOOD_AND_DRINK = vanillaGroup("food_and_drinks");
    private static final RegistryKey<ItemGroup> COMBAT = vanillaGroup("combat");
    private static final RegistryKey<ItemGroup> FUNCTIONAL = vanillaGroup("functional_blocks");
    private static final RegistryKey<ItemGroup> SPAWN_EGGS = vanillaGroup("spawn_eggs");

    private ModItemGroups() {
    }

    private static RegistryKey<ItemGroup> vanillaGroup(String path) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.ofVanilla(path));
    }

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(INGREDIENTS).register(entries -> {
            entries.add(ModItems.BOAR_TUSK);
            entries.add(ModItems.DEER_ANTLER);
        });

        ItemGroupEvents.modifyEntriesEvent(FOOD_AND_DRINK).register(entries -> {
            entries.add(ModItems.RAW_BOAR_MEAT);
            entries.add(ModItems.COOKED_BOAR_MEAT);
            entries.add(ModItems.RAW_VENISON);
            entries.add(ModItems.COOKED_VENISON);
            entries.add(ModItems.WILD_BERRIES);
        });

        ItemGroupEvents.modifyEntriesEvent(COMBAT).register(entries -> {
            entries.add(ModItems.BOAR_TUSK_DAGGER);
            entries.add(ModItems.ANTLER_GREATSWORD);
            entries.add(ModItems.HUNTER_BOW);
            entries.add(ModArmors.BOAR_HIDE_HELMET);
            entries.add(ModArmors.BOAR_HIDE_CHESTPLATE);
            entries.add(ModArmors.BOAR_HIDE_LEGGINGS);
            entries.add(ModArmors.BOAR_HIDE_BOOTS);
            entries.add(ModArmors.DEER_HIDE_HELMET);
            entries.add(ModArmors.DEER_HIDE_CHESTPLATE);
            entries.add(ModArmors.DEER_HIDE_LEGGINGS);
            entries.add(ModArmors.DEER_HIDE_BOOTS);
        });

        ItemGroupEvents.modifyEntriesEvent(FUNCTIONAL).register(entries -> {
            entries.add(ModVillagers.HUNTER_TABLE_ITEM);
            entries.add(ModHunting.BUTCHERING_TABLE_ITEM);
            entries.add(ModFarming.WILD_BERRY_BUSH_ITEM);
            entries.add(ModItems.CLASS_TOME);
        });

        ItemGroupEvents.modifyEntriesEvent(SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.WILD_BOAR_SPAWN_EGG);
            entries.add(ModItems.DEER_SPAWN_EGG);
            entries.add(ModItems.GUARD_VILLAGER_SPAWN_EGG);
            entries.add(ModItems.RIDEABLE_BOAR_SPAWN_EGG);
            entries.add(ModItems.FOREST_FOX_SPAWN_EGG);
            entries.add(ModItems.STAG_SPIRIT_SPAWN_EGG);
            entries.add(ModItems.FOREST_GUARDIAN_SPAWN_EGG);
        });
    }
}
