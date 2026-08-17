package com.adventuremod;

import com.adventuremod.entity.ModEntities;
import com.adventuremod.farming.ModFarming;
import com.adventuremod.hunting.ModHunting;
import com.adventuremod.item.ModItemGroups;
import com.adventuremod.item.ModItems;
import com.adventuremod.movement.AirJumpPayload;
import com.adventuremod.movement.DashPayload;
import com.adventuremod.movement.DashablePlayer;
import com.adventuremod.ranching.ModRanching;
import com.adventuremod.remodel.ModRemodels;
import com.adventuremod.rpg.ModRpg;
import com.adventuremod.skills.ModSkills;
import com.adventuremod.survival.ModSurvival;
import com.adventuremod.vanilla.ModVanilla;
import com.adventuremod.villager.ModVillagers;
import com.adventuremod.world.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventureMod implements ModInitializer {
    public static final String MOD_ID = "adventuremod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Adventure & Survival Expansion Mod...");

        ModItems.registerModItems();
        ModEntities.registerAttributes();
        ModVillagers.registerVillagers();
        ModWorldGen.registerWorldGen();
        ModFarming.registerFarming();
        ModHunting.registerHunting();
        ModRanching.registerRanching();
        ModSkills.registerSkills();
        ModRpg.registerRpg();
        ModSurvival.registerSurvival();
        ModVanilla.registerVanilla();
        ModRemodels.registerRemodels();
        ModItemGroups.registerItemGroups();

        PayloadTypeRegistry.playC2S().register(DashPayload.ID, DashPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(AirJumpPayload.ID, AirJumpPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(DashPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                if (context.player() instanceof DashablePlayer dashable) {
                    dashable.adventuremod$performDash();
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(AirJumpPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                if (payload.active() && context.player() instanceof DashablePlayer dashable) {
                    dashable.adventuremod$performAirJump();
                }
            });
        });

        LOGGER.info("Adventure & Survival Expansion Mod fully initialized!");
    }
}
