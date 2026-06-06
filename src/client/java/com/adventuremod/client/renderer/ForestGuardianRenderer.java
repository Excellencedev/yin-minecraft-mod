package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.bosses.ForestGuardianBossEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class ForestGuardianRenderer extends MobEntityRenderer<ForestGuardianBossEntity, PigEntityModel<ForestGuardianBossEntity>> {
    public ForestGuardianRenderer(EntityRendererFactory.Context context) {
        super(context, new PigEntityModel(context.getPart(EntityModelLayers.PIG)), 1.2F);
    }

    @Override
    public Identifier getTexture(ForestGuardianBossEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/forest_guardian.png");
    }
}
