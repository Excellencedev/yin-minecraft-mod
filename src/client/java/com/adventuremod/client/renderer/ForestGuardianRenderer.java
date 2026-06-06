package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.bosses.ForestGuardianBossEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.util.Identifier;

public class ForestGuardianRenderer extends MobEntityRenderer<ForestGuardianBossEntity, EntityModel<ForestGuardianBossEntity>> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ForestGuardianRenderer(EntityRendererFactory.Context context) {
        // RavagerEntityModel is no longer generic in 1.21.1 yarn; cast to EntityModel<Boss>.
        super(context, (EntityModel) new RavagerEntityModel(context.getPart(EntityModelLayers.RAVAGER)), 1.2F);
    }

    @Override
    public Identifier getTexture(ForestGuardianBossEntity entity) {
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/forest_guardian.png");
    }
}
