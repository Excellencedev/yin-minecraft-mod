package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.client.renderer.model.DeerEntityModel;
import com.adventuremod.client.renderer.model.ModEntityModelLayers;
import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, DeerEntityModel> {
    public DeerRenderer(EntityRendererFactory.Context context) {
        // Use our custom DEER layer (pig silhouette + antlers) rather than the
        // vanilla pig model, so the antlers the DeerEntityModel animates are
        // actually present in the rendered ModelPart tree.
        super(context, new DeerEntityModel(ModEntityModelLayers.getDeerRoot(context)), 0.6F);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        if (entity.hasAntlers()) {
            return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer_antlers.png");
        }
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer.png");
    }
}
