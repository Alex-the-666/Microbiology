package com.github.alexthe666.microbiology.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTeleporter extends ModelBase {
    public ModelRenderer top;
    public ModelRenderer bottom;

    public ModelTeleporter() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bottom = new ModelRenderer(this, 0, 0);
        this.bottom.setRotationPoint(0.0F, 3.5F, 0.0F);
        this.bottom.addBox(-7.0F, -1.5F, -7.0F, 14, 3, 14, 0.0F);
        this.top = new ModelRenderer(this, 0, 18);
        this.top.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.top.addBox(-8.0F, -3.0F, -8.0F, 16, 5, 16, 0.0F);
        this.top.addChild(this.bottom);
    }

    public void render(float f5) {
        this.top.render(f5);
    }
}
