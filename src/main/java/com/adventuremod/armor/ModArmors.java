package com.adventuremod.armor;

import com.adventuremod.AdventureMod;
import com.adventuremod.item.ModItems;
import net.minecraft.item.*;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class ModArmors {

    public static final ArmorMaterial BOAR_HIDE_MATERIAL = registerArmorMaterial("boar_hide",
            Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.HELMET, 2);
                map.put(EquipmentType.CHESTPLATE, 5);
                map.put(EquipmentType.LEGGINGS, 4);
                map.put(EquipmentType.BOOTS, 2);
            }), 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F, 0.0F,
            TagKey.of(Registries.ITEM.getKey(), Identifier.of(AdventureMod.MOD_ID, "boar_hide_repair")));

    public static final ArmorMaterial DEER_HIDE_MATERIAL = registerArmorMaterial("deer_hide",
            Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.HELMET, 2);
                map.put(EquipmentType.CHESTPLATE, 5);
                map.put(EquipmentType.LEGGINGS, 4);
                map.put(EquipmentType.BOOTS, 2);
            }), 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F, 0.0F,
            TagKey.of(Registries.ITEM.getKey(), Identifier.of(AdventureMod.MOD_ID, "deer_hide_repair")));

    public static final Item BOAR_HIDE_HELMET = registerArmorItem("boar_hide_helmet", BOAR_HIDE_MATERIAL, EquipmentType.HELMET);
    public static final Item BOAR_HIDE_CHESTPLATE = registerArmorItem("boar_hide_chestplate", BOAR_HIDE_MATERIAL, EquipmentType.CHESTPLATE);
    public static final Item BOAR_HIDE_LEGGINGS = registerArmorItem("boar_hide_leggings", BOAR_HIDE_MATERIAL, EquipmentType.LEGGINGS);
    public static final Item BOAR_HIDE_BOOTS = registerArmorItem("boar_hide_boots", BOAR_HIDE_MATERIAL, EquipmentType.BOOTS);

    public static final Item DEER_HIDE_HELMET = registerArmorItem("deer_hide_helmet", DEER_HIDE_MATERIAL, EquipmentType.HELMET);
    public static final Item DEER_HIDE_CHESTPLATE = registerArmorItem("deer_hide_chestplate", DEER_HIDE_MATERIAL, EquipmentType.CHESTPLATE);
    public static final Item DEER_HIDE_LEGGINGS = registerArmorItem("deer_hide_leggings", DEER_HIDE_MATERIAL, EquipmentType.LEGGINGS);
    public static final Item DEER_HIDE_BOOTS = registerArmorItem("deer_hide_boots", DEER_HIDE_MATERIAL, EquipmentType.BOOTS);

    private static ArmorMaterial registerArmorMaterial(String name, EnumMap<EquipmentType, Integer> protection, int enchantability,
                                                        net.minecraft.sound.SoundEvent equipSound, float toughness, float knockbackResistance,
                                                        TagKey<Item> repairTag) {
        return Registry.register(
                Registries.ARMOR_MATERIAL,
                Identifier.of(AdventureMod.MOD_ID, name),
                new ArmorMaterial(protection, enchantability, equipSound, toughness, knockbackResistance, repairTag)
        );
    }

    private static Item registerArmorItem(String name, ArmorMaterial material, EquipmentType type) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(AdventureMod.MOD_ID, name),
                new ArmorItem(material, type, new Item.Settings().maxCount(1).repairable(material.repairTag()))
        );
    }

    public static void registerArmors() {
        AdventureMod.LOGGER.info("Registering Armors for " + AdventureMod.MOD_ID);
    }
}
