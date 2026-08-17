package com.adventuremod.client.renderer.model;

import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PigEntityModel;

/**
 * Custom deer model.
 *
 * Built on top of the vanilla {@link PigEntityModel} silhouette (we reuse the
 * pig's body/leg geometry to avoid mapping drift) and adds an antler cuboid
 * parented to the head. In the modern (1.21) Yarn {@code ModelPart} tree the
 * whole model — body, legs, head and the extra antlers child — is declared
 * declaratively in {@link #getTexturedModelData(Dilation)}; the model class
 * itself just grabs handles to the parts it wants to animate.
 *
 * The antler sway toggles in {@link #setAngles(DeerEntity, float, float, float,
 * float, float)} on top of the vanilla quadruped walk, and the whole antler
 * part hides when the deer has shed them.
 */
public class DeerEntityModel extends PigEntityModel<DeerEntity> {
    /** Antler cuboid parented to the head; animated in {@link #setAngles}. */
    public final ModelPart antlers;

    public DeerEntityModel(ModelPart root) {
        super(root);
        // PigEntityModel assigns its own fields (head/body/legs) from the loaded
        // ModelPart tree. We declared an extra "antlers" child under "head" in
        // getTexturedModelData so it inherits the head's pivot/rotation; here we
        // grab it from the head part for animation.
        this.antlers = root.getChild("head").getChild("antlers");
    }

    /**
     * Builds the textured model data: the vanilla quadruped (pig) geometry
     * with an extra {@code antlers} child attached to {@code head}. Registered
     * client-side via
     * {@link net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry}
     * against {@link ModEntityModelLayers#DEER}. The cuboid layout mirrors the
     * vanilla quadruped so the pig texture maps correctly.
     */
    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        // Body — the pig torso.
        root.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(28, 8).cuboid(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, dilation),
                ModelTransform.pivot(-5.0F, 6.0F, -7.0F)
        );
        // Legs — four identical legs anchored to the body underside.
        root.addChild(
                "right_hind_leg",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, dilation),
                ModelTransform.pivot(-7.0F, 6.0F, 7.0F)
        );
        root.addChild(
                "left_hind_leg",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, dilation),
                ModelTransform.pivot(-7.0F, 6.0F, -5.0F)
        );
        root.addChild(
                "right_front_leg",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, dilation),
                ModelTransform.pivot(-1.0F, 6.0F, 7.0F)
        );
        root.addChild(
                "left_front_leg",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, dilation),
                ModelTransform.pivot(-1.0F, 6.0F, -5.0F)
        );
        // Head — the pig head. Build it first, then attach an antlers child to
        // the returned head ModelPartData so the antlers inherit the head's
        // pivot/rotation. The two antler cuboids form a pair of forward-pointing
        // horns rising above the head; they are hidden when the deer has shed
        // them (see setAngles).
        ModelPartData head = root.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, dilation)
                        .uv(16, 16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, dilation),
                ModelTransform.pivot(-4.0F, 4.0F, -8.0F)
        );
        head.addChild(
                "antlers",
                ModelPartBuilder.create()
                        .uv(40, 0).cuboid(-1.5F, -6.0F, -4.0F, 1.0F, 6.0F, 1.0F)
                        .uv(44, 0).cuboid(0.5F, -6.0F, -4.0F, 1.0F, 6.0F, 1.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 32);
    }

    @Override
    public void setAngles(DeerEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // Hide the antlers entirely when the deer has shed them; the data
        // tracker is synced to clients, so this reflects the real state.
        this.antlers.visible = entity.hasAntlers();

        // Gentle sway while walking; static when standing. Adds a small
        // animation touch on top of the vanilla quadruped walk.
        float sway = (float) Math.sin(ageInTicks * 0.15F) * 0.15F * limbSwingAmount;
        this.antlers.roll = sway;
    }
}
