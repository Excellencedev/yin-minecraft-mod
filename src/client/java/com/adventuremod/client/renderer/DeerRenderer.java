package com.adventuremod.client.renderer;

import com.adventuremod.AdventureMod;
import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, EntityModel<DeerEntity>> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DeerRenderer(EntityRendererFactory.Context context) {
        // GoatEntityModel is bounded to GoatEntity; we cast it to a raw EntityModel<DeerEntity>
        // so the renderer can be parameterized on DeerEntity. The model still renders the
        // deer visually; it just isn't strictly type-checked at compile time.
        super(context, (EntityModel) new GoatEntityModel(context.getPart(EntityModelLayers.GOAT)), 0.6F);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        if (entity.hasAntlers()) {
            return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer_antlers.png");
        }
        return Identifier.of(AdventureMod.MOD_ID, "textures/entity/deer.png");
    }
}
