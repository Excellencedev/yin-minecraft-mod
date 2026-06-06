package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.pets.ForestFoxEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.util.Identifier;

public class ForestFoxRenderer extends MobEntityRenderer<ForestFoxEntity, EntityModel<ForestFoxEntity>> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ForestFoxRenderer(EntityRendererFactory.Context context) {
        // FoxEntityModel is bounded to FoxEntity; cast to EntityModel<ForestFoxEntity> for the
        // renderer's type parameter. Model renders visually; type safety is bypassed.
        super(context, (EntityModel) new FoxEntityModel(context.getPart(EntityModelLayers.FOX)), 0.4F);
    }

    @Override
    public Identifier getTexture(ForestFoxEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/forest_fox.png");
    }
}
