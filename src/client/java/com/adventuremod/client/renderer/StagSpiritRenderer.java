package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.mythics.StagSpiritEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class StagSpiritRenderer extends MobEntityRenderer<StagSpiritEntity, PigEntityModel<StagSpiritEntity>> {
    public StagSpiritRenderer(EntityRendererFactory.Context context) {
        super(context, new PigEntityModel(context.getPart(EntityModelLayers.PIG)), 1.0F);
    }

    @Override
    public Identifier getTexture(StagSpiritEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/stag_spirit.png");
    }
}
