package com.github.alexthe666.microbiology.client.render.tile;

import com.github.alexthe666.microbiology.client.model.ModelTeleporter;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityTeleporter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTeleporter extends TileEntitySpecialRenderer<TileEntityTeleporter> {
    private static final ResourceLocation ACTIVE_TEXTURE = new ResourceLocation("microbiology:textures/models/teleporter_on.png");
    private static final ResourceLocation INACTIVE_TEXTURE = new ResourceLocation("microbiology:textures/models/teleporter_off.png");
    private static final ModelTeleporter MODEL = new ModelTeleporter();

    public RenderTeleporter() {
    }

    @Override
    public void render(TileEntityTeleporter entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        boolean active = false;
        int i = 180;
        if (entity != null && entity.hasWorld()) {
            active = entity.getBlockType() == MicrobiologyBlockRegistry.TELEPORTER_ON;
            i = entity.isTop() ? 0 : 180;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + (i == 180 ? 1.5F : -0.5F), (float) z + 0.5F);
        GL11.glRotatef(i, 1.0F, 0, 0);
        GL11.glPushMatrix();
        this.bindTexture(active ? ACTIVE_TEXTURE : INACTIVE_TEXTURE);
        GL11.glPushMatrix();
        MODEL.render(0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();

    }

}
