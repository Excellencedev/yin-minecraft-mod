package com.adventuremod.entity;

import com.adventuremod.AdventureMod;
import com.adventuremod.bosses.ForestGuardianBossEntity;
import com.adventuremod.mount.RideableBoarEntity;
import com.adventuremod.mythics.StagSpiritEntity;
import com.adventuremod.pets.ForestFoxEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<WildBoarEntity> WILD_BOAR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "wild_boar"),
            EntityType.Builder.create(WildBoarEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.9F, 0.9F)
                    .build("wild_boar")
    );

    public static final EntityType<DeerEntity> DEER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "deer"),
            EntityType.Builder.create(DeerEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.8F, 1.6F)
                    .build("deer")
    );

    public static final EntityType<GuardVillagerEntity> GUARD_VILLAGER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "guard_villager"),
            EntityType.Builder.create(GuardVillagerEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6F, 1.95F)
                    .build("guard_villager")
    );

    public static final EntityType<RideableBoarEntity> RIDEABLE_BOAR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "rideable_boar"),
            EntityType.Builder.create(RideableBoarEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.9F, 0.9F)
                    .build("rideable_boar")
    );

    public static final EntityType<ForestFoxEntity> FOREST_FOX = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "forest_fox"),
            EntityType.Builder.create(ForestFoxEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6F, 0.7F)
                    .build("forest_fox")
    );

    public static final EntityType<StagSpiritEntity> STAG_SPIRIT = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "stag_spirit"),
            EntityType.Builder.create(StagSpiritEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.2F, 2.0F)
                    .build("stag_spirit")
    );

    public static final EntityType<ForestGuardianBossEntity> FOREST_GUARDIAN = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(AdventureMod.MOD_ID, "forest_guardian"),
            EntityType.Builder.create(ForestGuardianBossEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.6F, 2.8F)
                    .build("forest_guardian")
    );

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(WILD_BOAR, WildBoarEntity.createBoarAttributes());
        FabricDefaultAttributeRegistry.register(DEER, DeerEntity.createDeerAttributes());
        FabricDefaultAttributeRegistry.register(GUARD_VILLAGER, GuardVillagerEntity.createGuardAttributes());
        FabricDefaultAttributeRegistry.register(RIDEABLE_BOAR, RideableBoarEntity.createMountAttributes());
        FabricDefaultAttributeRegistry.register(FOREST_FOX, ForestFoxEntity.createFoxAttributes());
        FabricDefaultAttributeRegistry.register(STAG_SPIRIT, StagSpiritEntity.createStagAttributes());
        FabricDefaultAttributeRegistry.register(FOREST_GUARDIAN, ForestGuardianBossEntity.createBossAttributes());
    }
}
