package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.mythics.StagSpiritEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Identifier;

public class StagSpiritRenderer extends MobEntityRenderer<StagSpiritEntity, GoatEntityModel<GoatEntity>> {
    public StagSpiritRenderer(EntityRendererFactory.Context context) {
        super(context, new GoatEntityModel(context.getPart(EntityModelLayers.GOAT)), 1.0F);
    }

    @Override
    public Identifier getTexture(StagSpiritEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/stag_spirit.png");
    }
}
