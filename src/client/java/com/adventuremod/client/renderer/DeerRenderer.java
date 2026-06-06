package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, PigEntityModel<DeerEntity>> {
    public DeerRenderer(EntityRendererFactory.Context context) {
        super(context, new PigEntityModel(context.getPart(EntityModelLayers.PIG)), 0.6F);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        if (entity.hasAntlers()) {
            return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer_antlers.png");
        }
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer.png");
    }
}
