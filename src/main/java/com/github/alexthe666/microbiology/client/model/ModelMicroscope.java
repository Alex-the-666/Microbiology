package com.github.alexthe666.microbiology.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMicroscope extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer stem1;
    public ModelRenderer stem2;
    public ModelRenderer head1;
    public ModelRenderer head2;
    public ModelRenderer objectiveTurret;
    public ModelRenderer eyepiece1;
    public ModelRenderer eyepiece1_1;
    public ModelRenderer objective1;
    public ModelRenderer objective2;
    public ModelRenderer objective3;

    public ModelMicroscope() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.base = new ModelRenderer(this, 0, 48);
        this.base.setRotationPoint(0.0F, 23.5F, 0.0F);
        this.base.addBox(-7.0F, -1.5F, -7.0F, 14, 2, 14, 0.0F);
        this.head2 = new ModelRenderer(this, 15, 18);
        this.head2.setRotationPoint(0.0F, 0.8F, -3.4F);
        this.head2.addBox(-3.0F, -2.5F, -3.0F, 6, 2, 6, 0.0F);
        this.head1 = new ModelRenderer(this, 24, 35);
        this.head1.setRotationPoint(0.0F, -6.8F, -0.3F);
        this.head1.addBox(-4.5F, 0.0F, -8.0F, 9, 2, 11, 0.0F);
        this.setRotateAngle(head1, -0.2617993877991494F, 0.0F, 0.0F);
        this.stem1 = new ModelRenderer(this, 0, 48);
        this.stem1.setRotationPoint(0.0F, -1.0F, 3.5F);
        this.stem1.addBox(-1.5F, -6.0F, -1.5F, 3, 7, 3, 0.0F);
        this.setRotateAngle(stem1, -0.2617993877991494F, 0.0F, 0.0F);
        this.objective2 = new ModelRenderer(this, 10, 17);
        this.objective2.setRotationPoint(1.4F, 1.7F, 1.4F);
        this.objective2.addBox(-1.0F, 0.5F, -1.0F, 2, 4, 2, 0.0F);
        this.objective1 = new ModelRenderer(this, 0, 29);
        this.objective1.setRotationPoint(0.0F, 2.5F, -1.4F);
        this.objective1.addBox(-1.0F, 0.5F, -1.0F, 2, 4, 2, 0.0F);
        this.eyepiece1 = new ModelRenderer(this, 0, 0);
        this.eyepiece1.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.eyepiece1.addBox(-1.0F, -10.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(eyepiece1, -0.7740535232594852F, 0.0F, 0.0F);
        this.eyepiece1_1 = new ModelRenderer(this, 0, 22);
        this.eyepiece1_1.setRotationPoint(0.0F, -0.8F, 0.0F);
        this.eyepiece1_1.addBox(-1.5F, -10.0F, -1.5F, 3, 2, 3, 0.0F);
        this.objectiveTurret = new ModelRenderer(this, 14, 29);
        this.objectiveTurret.setRotationPoint(0.0F, 0.8F, -4.5F);
        this.objectiveTurret.addBox(-2.5F, 1.0F, -2.5F, 5, 2, 5, 0.0F);
        this.objective3 = new ModelRenderer(this, 0, 10);
        this.objective3.setRotationPoint(-1.4F, 1.0F, 1.4F);
        this.objective3.addBox(-1.0F, 0.5F, -1.0F, 2, 4, 2, 0.0F);
        this.stem2 = new ModelRenderer(this, 0, 36);
        this.stem2.setRotationPoint(0.0F, -6.0F, -0.3F);
        this.stem2.addBox(-1.5F, -6.0F, -1.5F, 3, 7, 3, 0.0F);
        this.setRotateAngle(stem2, 0.5235987755982988F, 0.0F, 0.0F);
        this.head1.addChild(this.head2);
        this.stem2.addChild(this.head1);
        this.base.addChild(this.stem1);
        this.objectiveTurret.addChild(this.objective2);
        this.objectiveTurret.addChild(this.objective1);
        this.head2.addChild(this.eyepiece1);
        this.eyepiece1.addChild(this.eyepiece1_1);
        this.head1.addChild(this.objectiveTurret);
        this.objectiveTurret.addChild(this.objective3);
        this.stem1.addChild(this.stem2);
    }

    public void render(float f5) {
        this.base.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
