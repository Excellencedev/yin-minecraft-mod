package com.adventuremod.item;

import com.adventuremod.AdventureMod;
import com.adventuremod.armor.ModArmors;
import com.adventuremod.entity.ModEntities;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final FoodComponent BOAR_MEAT_FOOD = new FoodComponent.Builder()
            .nutrition(3)
            .saturation(1.8f)
            .build();

    public static final FoodComponent COOKED_BOAR_MEAT_FOOD = new FoodComponent.Builder()
            .nutrition(8)
            .saturation(12.8f)
            .build();

    public static final FoodComponent VENISON_FOOD = new FoodComponent.Builder()
            .nutrition(3)
            .saturation(1.8f)
            .build();

    public static final FoodComponent COOKED_VENISON_FOOD = new FoodComponent.Builder()
            .nutrition(8)
            .saturation(12.8f)
            .build();

    public static final Item BOAR_TUSK = registerItem("boar_tusk", new Item(new Item.Settings()));
    public static final Item DEER_ANTLER = registerItem("deer_antler", new Item(new Item.Settings()));

    public static final Item RAW_BOAR_MEAT = registerItem("raw_boar_meat", new Item(new Item.Settings().food(BOAR_MEAT_FOOD)));
    public static final Item COOKED_BOAR_MEAT = registerItem("cooked_boar_meat", new Item(new Item.Settings().food(COOKED_BOAR_MEAT_FOOD)));

    public static final Item RAW_VENISON = registerItem("raw_venison", new Item(new Item.Settings().food(VENISON_FOOD)));
    public static final Item COOKED_VENISON = registerItem("cooked_venison", new Item(new Item.Settings().food(COOKED_VENISON_FOOD)));

    public static final Item BOAR_TUSK_DAGGER = registerItem("boar_tusk_dagger", new BoarTuskDaggerItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 2, -1.8f))));
    public static final Item ANTLER_GREATSWORD = registerItem("antler_greatsword", new AntlerGreatswordItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 7, -3.2f))));
    public static final Item HUNTER_BOW = registerItem("hunter_bow", new HunterBowItem(new Item.Settings().maxDamage(450)));

    public static final Item WILD_BERRIES = registerItem("wild_berries", new WildBerryItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(1).saturation(0.3f).build())));
    public static final Item CLASS_TOME = registerItem("class_tome", new ClassTomeItem(new Item.Settings().maxCount(1)));

    public static final Item WILD_BOAR_SPAWN_EGG = registerItem("wild_boar_spawn_egg",
            new SpawnEggItem(ModEntities.WILD_BOAR, 0x8B4513, 0x654321, new Item.Settings()));
    public static final Item DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            new SpawnEggItem(ModEntities.DEER, 0xA06030, 0x7A4E28, new Item.Settings()));
    public static final Item GUARD_VILLAGER_SPAWN_EGG = registerItem("guard_villager_spawn_egg",
            new SpawnEggItem(ModEntities.GUARD_VILLAGER, 0x3C3CB4, 0x5050C8, new Item.Settings()));
    public static final Item RIDEABLE_BOAR_SPAWN_EGG = registerItem("rideable_boar_spawn_egg",
            new SpawnEggItem(ModEntities.RIDEABLE_BOAR, 0x8B4513, 0x654321, new Item.Settings()));
    public static final Item FOREST_FOX_SPAWN_EGG = registerItem("forest_fox_spawn_egg",
            new SpawnEggItem(ModEntities.FOREST_FOX, 0xC87828, 0x32B032, new Item.Settings()));
    public static final Item STAG_SPIRIT_SPAWN_EGG = registerItem("stag_spirit_spawn_egg",
            new SpawnEggItem(ModEntities.STAG_SPIRIT, 0xB4DCFF, 0xFFFFC8, new Item.Settings()));
    public static final Item FOREST_GUARDIAN_SPAWN_EGG = registerItem("forest_guardian_spawn_egg",
            new SpawnEggItem(ModEntities.FOREST_GUARDIAN, 0x3C5028, 0x00FF00, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AdventureMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AdventureMod.LOGGER.info("Registering Mod Items for " + AdventureMod.MOD_ID);
        ModArmors.registerArmors();
    }
}
