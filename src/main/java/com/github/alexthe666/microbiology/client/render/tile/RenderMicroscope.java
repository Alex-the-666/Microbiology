package com.github.alexthe666.microbiology.client.render.tile;

import com.github.alexthe666.microbiology.client.model.ModelMicroscope;
import com.github.alexthe666.microbiology.server.block.BlockMicroscope;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityMicroscope;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderMicroscope extends TileEntitySpecialRenderer<TileEntityMicroscope> {
    private static final ResourceLocation ACTIVE_TEXTURE = new ResourceLocation("microbiology:textures/models/microscope_on.png");
    private static final ResourceLocation INACTIVE_TEXTURE = new ResourceLocation("microbiology:textures/models/microscope_off.png");
    private static final ModelMicroscope MODEL = new ModelMicroscope();

    public RenderMicroscope() {
    }

    @Override
    public void render(TileEntityMicroscope entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        boolean active = entity != null && entity.hasWorld() && entity.getBlockType() == MicrobiologyBlockRegistry.MICROSCOPE_ON;
        short short1 = 0;
        if (entity != null && entity.hasWorld()) {
            int i  = entity.getBlockType().getStateFromMeta(entity.getBlockMetadata()).getValue(BlockMicroscope.FACING).getHorizontalIndex();
            switch(i){
                case 0:
                    short1 = 180;
                    break;
                case 1:
                    short1 = 90;
                    break;
                case 2:
                    short1 = 0;
                    break;
                case 3:
                    short1 = -90;
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(short1, 0, 1.0F, 0);
        GL11.glRotatef(180, 1F, 0, 0);
        GL11.glPushMatrix();
        this.bindTexture(active ? ACTIVE_TEXTURE : INACTIVE_TEXTURE);
        GL11.glPushMatrix();
        MODEL.render(0.0625F);
        GL11.glPopMatrix();
        if (entity != null && entity.hasWorld() && entity.getStackInSlot(0) != null && entity.getStackInSlot(0).getItem() != null && entity.getStackInSlot(0).getItem() == MicrobiologyItemRegistry.PETRI_DISH) {
            GL11.glTranslatef(0, 1.25F, -0.2F);
            GL11.glRotatef(180, 1F, 0, 0);
            GL11.glScalef(0.35F, 0.35F, 0.35F);
            GL11.glRotatef(entity.tick % 360, 0, 1F, 0);
            Minecraft.getMinecraft().getRenderItem().renderItem(entity.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);

        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();

    }

}
