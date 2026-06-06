package com.adventuremod.armor;

import com.adventuremod.AdventureMod;
import com.adventuremod.item.ModItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmors {

    public static final RegistryEntry<ArmorMaterial> BOAR_HIDE_MATERIAL = registerArmorMaterial("boar_hide",
            Map.of(
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 5,
                    ArmorItem.Type.LEGGINGS, 4,
                    ArmorItem.Type.BOOTS, 2
            ), 10, RegistryEntry.of(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER), 0.5F, 0.0F,
            () -> Ingredient.ofItems(ModItems.BOAR_TUSK));

    public static final RegistryEntry<ArmorMaterial> DEER_HIDE_MATERIAL = registerArmorMaterial("deer_hide",
            Map.of(
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 5,
                    ArmorItem.Type.LEGGINGS, 4,
                    ArmorItem.Type.BOOTS, 2
            ), 12, RegistryEntry.of(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER), 0.5F, 0.0F,
            () -> Ingredient.ofItems(ModItems.DEER_ANTLER));

    public static final Item BOAR_HIDE_HELMET = registerArmorItem("boar_hide_helmet", BOAR_HIDE_MATERIAL, ArmorItem.Type.HELMET);
    public static final Item BOAR_HIDE_CHESTPLATE = registerArmorItem("boar_hide_chestplate", BOAR_HIDE_MATERIAL, ArmorItem.Type.CHESTPLATE);
    public static final Item BOAR_HIDE_LEGGINGS = registerArmorItem("boar_hide_leggings", BOAR_HIDE_MATERIAL, ArmorItem.Type.LEGGINGS);
    public static final Item BOAR_HIDE_BOOTS = registerArmorItem("boar_hide_boots", BOAR_HIDE_MATERIAL, ArmorItem.Type.BOOTS);

    public static final Item DEER_HIDE_HELMET = registerArmorItem("deer_hide_helmet", DEER_HIDE_MATERIAL, ArmorItem.Type.HELMET);
    public static final Item DEER_HIDE_CHESTPLATE = registerArmorItem("deer_hide_chestplate", DEER_HIDE_MATERIAL, ArmorItem.Type.CHESTPLATE);
    public static final Item DEER_HIDE_LEGGINGS = registerArmorItem("deer_hide_leggings", DEER_HIDE_MATERIAL, ArmorItem.Type.LEGGINGS);
    public static final Item DEER_HIDE_BOOTS = registerArmorItem("deer_hide_boots", DEER_HIDE_MATERIAL, ArmorItem.Type.BOOTS);

    private static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Map<ArmorItem.Type, Integer> defense,
                                                                      int enchantability, RegistryEntry<net.minecraft.sound.SoundEvent> equipSound,
                                                                      float toughness, float knockbackResistance,
                                                                      Supplier<Ingredient> repairIngredient) {
        Identifier id = Identifier.of(AdventureMod.MOD_ID, name);
        ArmorMaterial material = new ArmorMaterial(defense, enchantability, equipSound, repairIngredient, List.of(), toughness, knockbackResistance);
        return RegistryEntry.of(Registry.register(Registries.ARMOR_MATERIAL, id, material));
    }

    private static Item registerArmorItem(String name, RegistryEntry<ArmorMaterial> material, ArmorItem.Type type) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(AdventureMod.MOD_ID, name),
                new ArmorItem(material, type, new Item.Settings().maxCount(1))
        );
    }

    public static void registerArmors() {
        AdventureMod.LOGGER.info("Registering Armors for " + AdventureMod.MOD_ID);
    }
}
