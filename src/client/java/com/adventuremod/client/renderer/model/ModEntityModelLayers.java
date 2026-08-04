package com.adventuremod.client.renderer.model;

import com.adventuremod.client.AdventureModClient;
import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * Holds the custom client-side model-layer registry entries for the mod.
 *
 * The mod deliberately keeps its custom entities on vanilla-derived models
 * (pig, fox, villager) so we don't have to hand-roll geometry that risks
 * mapping drift. The one exception is the {@code deer}: a custom layer is
 * registered so a {@link DeerEntityModel} can add an antler cuboid on top of
 * the pig silhouette — the deer's most recognisable remodel.
 */
public final class ModEntityModelLayers {
    public static final EntityModelLayer DEER = new EntityModelLayer(
            Identifier.of("adventuremod", "deer"), "main");

    private ModEntityModelLayers() {
    }

    /**
     * Convenience accessor used by the deer renderer to fetch its model part
     * from the {@link net.minecraft.client.render.entity.model.EntityModelLayers}
     * context passed at construction time.
     */
    public static ModelPart getDeerRoot(net.minecraft.client.render.entity.EntityRendererFactory.Context context) {
        return context.getPart(DEER);
    }
}
