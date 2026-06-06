package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, GoatEntityModel<DeerEntity>> {
    public DeerRenderer(EntityRendererFactory.Context context) {
        super(context, new GoatEntityModel<>(context.getPart(EntityModelLayers.GOAT)), 0.6F);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        if (entity.hasAntlers()) {
            return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer_antlers.png");
        }
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer.png");
    }
}
