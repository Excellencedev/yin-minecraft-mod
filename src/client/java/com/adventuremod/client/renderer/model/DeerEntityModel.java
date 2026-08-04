package com.adventuremod.client.renderer.model;

import com.adventuremod.entity.DeerEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.QuadrupedEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;

/**
 * Custom deer model.
 *
 * Built on top of the vanilla {@link PigEntityModel} silhouette (we keep the
 * pig's body/leg geometry to avoid mapping drift), but adds an antler
 * cuboid parented to the head. The antler is swayed slightly during the
 * walk/idle cycle in {@link #setupAnim(DeerEntity, float, float, float, float, float)}
 * — giving the deer its characteristic remodel + a touch of animation beyond
 * the vanilla quadruped walk.
 */
public class DeerEntityModel extends PigEntityModel<DeerEntity> {
    /** Antler cuboid parented to the head; animated in {@link #setupAnim}. */
    public final ModelPart antlers;

    public DeerEntityModel(ModelPart root) {
        super(root);
        // PigEntityModel assigns its own fields from the loaded ModelPart
        // tree (head/body/legs). We add an antler child to the head so it
        // inherits the head's pivot/rotation.
        this.antlers = new ModelPart(this);
        // A pair of antlers: two forward-pointing horns rising above the head.
        this.antlers.setTextureOffset(0, 0).cuboid(-1.5F, -6.0F, -4.0F, 1.0F, 6.0F, 1.0F);
        this.antlers.setTextureOffset(4, 0).cuboid(0.5F, -6.0F, -4.0F, 1.0F, 6.0F, 1.0F);
        this.head.addChild(this.antlers);
    }

    @Override
    public void setupAnim(DeerEntity entity, float limbSwing, float limbSwingAmount,
                           float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // Hide the antlers entirely when the deer has shed them; the data
        // tracker is synced to clients, so this reflects the real state.
        this.antlers.visible = entity.hasAntlers();

        // Gentle sway while walking; static when standing. Adds a small
        // animation touch on top of the vanilla quadruped walk.
        float sway = (float) Math.sin(ageInTicks * 0.15F) * 0.15F * limbSwingAmount;
        this.antlers.roll = sway;
    }
}
