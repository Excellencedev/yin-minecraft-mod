package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.pets.ForestFoxEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.util.Identifier;

public class ForestFoxRenderer extends MobEntityRenderer<ForestFoxEntity, FoxEntityModel<ForestFoxEntity>> {
    public ForestFoxRenderer(EntityRendererFactory.Context context) {
        super(context, new FoxEntityModel<>(context.getPart(EntityModelLayers.FOX)), 0.4F);
    }

    @Override
    public Identifier getTexture(ForestFoxEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/forest_fox.png");
    }
}
