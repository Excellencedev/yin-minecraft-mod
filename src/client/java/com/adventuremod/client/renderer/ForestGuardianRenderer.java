package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.bosses.ForestGuardianBossEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.Identifier;

public class ForestGuardianRenderer extends MobEntityRenderer<ForestGuardianBossEntity, RavagerEntityModel<RavagerEntity>> {
    public ForestGuardianRenderer(EntityRendererFactory.Context context) {
        super(context, new RavagerEntityModel(context.getPart(EntityModelLayers.RAVAGER)), 1.2F);
    }

    @Override
    public Identifier getTexture(ForestGuardianBossEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/forest_guardian.png");
    }
}
