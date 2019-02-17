package com.github.alexthe666.microbiology.client.render.sky;

import net.ilexiconn.llibrary.LLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class MicrobiologySkyRenderer extends IRenderHandler {
    private static final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("microbiology:textures/clouds.png");
    private int skyboxList = -1;

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        double yTranslate = -Minecraft.getMinecraft().player.posY * 0.015;
        if (this.skyboxList == -1) {
            GlStateManager.pushMatrix();
            this.skyboxList = GLAllocation.generateDisplayLists(1);
            GlStateManager.glNewList(this.skyboxList, GL11.GL_COMPILE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            double scale = 1.0 / 3.0;

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-8.0, 8.0, -8.0).tex(0.0, 0.5).endVertex();
            buffer.pos(8.0, 8.0, -8.0).tex(scale, 0.5).endVertex();
            buffer.pos(8.0, -8.0, -8.0).tex(scale, 1.0).endVertex();
            buffer.pos(-8.0, -8.0, -8.0).tex(0.0, 1.0).endVertex();
            tessellator.draw();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-8.0, 8.0, 8.0).tex(scale * 3.0F, 0.5).endVertex();
            buffer.pos(8.0, 8.0, 8.0).tex(scale * 2.0F, 0.5).endVertex();
            buffer.pos(8.0, -8.0, 8.0).tex(scale * 2.0F, 1.0).endVertex();
            buffer.pos(-8.0, -8.0, 8.0).tex(scale * 3.0F, 1.0).endVertex();
            tessellator.draw();

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-8.0, 8.0, -8.0).tex(1.0, 0.0).endVertex();
            buffer.pos(-8.0, 8.0, 8.0).tex(1.0 - scale, 0.0).endVertex();
            buffer.pos(-8.0, -8.0, 8.0).tex(1.0 - scale, 0.5).endVertex();
            buffer.pos(-8.0, -8.0, -8.0).tex(1.0, 0.5).endVertex();
            tessellator.draw();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(8.0, 8.0, -8.0).tex(scale, 0.5).endVertex();
            buffer.pos(8.0, 8.0, 8.0).tex(scale * 2.0F, 0.5).endVertex();
            buffer.pos(8.0, -8.0, 8.0).tex(scale * 2.0F, 1.0).endVertex();
            buffer.pos(8.0, -8.0, -8.0).tex(scale, 1.0).endVertex();
            tessellator.draw();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-8.0, -8.0, 8.0).tex(scale, 0.5).endVertex();
            buffer.pos(8.0, -8.0, 8.0).tex(scale, 0.0).endVertex();
            buffer.pos(8.0, -8.0, -8.0).tex(0.0, 0.0).endVertex();
            buffer.pos(-8.0, -8.0, -8.0).tex(0.0, 0.5).endVertex();
            tessellator.draw();//TOP
            GL11.glPushMatrix();
            GlStateManager.translate(0.0F, -yTranslate, 0.0F);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-8.0, 8.0, 8.0).tex(scale * 2.0F, 0.0).endVertex();
            buffer.pos(8.0, 8.0, 8.0).tex(scale * 2.0F, 0.5).endVertex();
            buffer.pos(8.0, 8.0, -8.0).tex(scale, 0.5).endVertex();
            buffer.pos(-8.0, 8.0, -8.0).tex(scale, 0.0).endVertex();
            tessellator.draw();
            GL11.glPopMatrix();
            GlStateManager.glEndList();
            GlStateManager.popMatrix();
        }

        GlStateManager.pushMatrix();

        mc.renderEngine.bindTexture(CLOUDS_TEXTURES);

        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableFog();
        GlStateManager.scale(9.0F, 9.0F, 9.0F);

        float color = 2 - mc.world.getSunBrightnessFactor(partialTicks);
        GlStateManager.translate(0.0F, yTranslate, 0.0F);
        GlStateManager.color(color, color, color, color * 1.0F);

        GlStateManager.callList(this.skyboxList);

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.5F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
