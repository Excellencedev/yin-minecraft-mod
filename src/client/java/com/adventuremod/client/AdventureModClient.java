package com.adventuremod.client;

import com.adventuremod.client.renderer.*;
import com.adventuremod.client.renderer.model.DeerEntityModel;
import com.adventuremod.client.renderer.model.ModEntityModelLayers;
import com.adventuremod.entity.ModEntities;
import com.adventuremod.movement.DashPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AdventureModClient implements ClientModInitializer {
    private static KeyBinding dashKeyBinding;

    @Override
    public void onInitializeClient() {
        // Register the custom deer model layer (pig silhouette + antlers) so the
        // DeerRenderer can fetch its ModelPart via ModEntityModelLayers.DEER.
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.DEER,
                () -> DeerEntityModel.getTexturedModelData(Dilation.NONE)
        );

        EntityRendererRegistry.register(ModEntities.WILD_BOAR, WildBoarRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEER, DeerRenderer::new);
        EntityRendererRegistry.register(ModEntities.GUARD_VILLAGER, GuardVillagerRenderer::new);
        EntityRendererRegistry.register(ModEntities.RIDEABLE_BOAR, RideableBoarRenderer::new);
        EntityRendererRegistry.register(ModEntities.FOREST_FOX, ForestFoxRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAG_SPIRIT, StagSpiritRenderer::new);
        EntityRendererRegistry.register(ModEntities.FOREST_GUARDIAN, ForestGuardianRenderer::new);

        dashKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.adventuremod.dash",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "category.adventuremod.movement"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                while (dashKeyBinding.wasPressed()) {
                    ClientPlayNetworking.send(new DashPayload(true));
                }
            }
        });
    }
}
