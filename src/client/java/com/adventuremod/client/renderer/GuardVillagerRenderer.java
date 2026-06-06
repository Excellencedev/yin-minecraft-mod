package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.GuardVillagerEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class GuardVillagerRenderer extends BipedEntityRenderer<GuardVillagerEntity, BipedEntityModel<GuardVillagerEntity>> {
    public GuardVillagerRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(GuardVillagerEntity entity) {
        if (entity.isTamed()) {
            return Identifier.of(AdventureMod.MOD_ID, "textures/entity/guard_villager_hired.png");
        }
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/guard_villager.png");
    }
}
