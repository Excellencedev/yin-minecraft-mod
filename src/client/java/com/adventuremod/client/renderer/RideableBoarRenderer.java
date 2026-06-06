package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.mount.RideableBoarEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class RideableBoarRenderer extends MobEntityRenderer<RideableBoarEntity, PigEntityModel<RideableBoarEntity>> {
    public RideableBoarRenderer(EntityRendererFactory.Context context) {
        super(context, new PigEntityModel<>(context.getPart(EntityModelLayers.PIG)), 0.7F);
    }

    @Override
    public Identifier getTexture(RideableBoarEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/wild_boar.png");
    }
}
