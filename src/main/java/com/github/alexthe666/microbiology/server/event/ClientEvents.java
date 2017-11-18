package com.github.alexthe666.microbiology.server.event;

import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.MicrobiologyFluidRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEvents {
    public static final ResourceLocation UNDERWATER = new ResourceLocation("microbiology:textures/underwater.png");

    @SubscribeEvent
    public void onBlockOverlay(RenderBlockOverlayEvent e) {
        Block block = e.getPlayer().world.getBlockState(e.getBlockPos()).getBlock();
        if (block == MicrobiologyFluidRegistry.OOZE || block == MicrobiologyBlockRegistry.MICROBIAL_SEDIMENT) {
            e.setCanceled(true);
            Minecraft.getMinecraft().getTextureManager().bindTexture(UNDERWATER);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            float f = Minecraft.getMinecraft().player.getBrightness();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();
            float f1 = 4.0F;
            float f2 = -1.0F;
            float f3 = 1.0F;
            float f4 = -1.0F;
            float f5 = 1.0F;
            float f6 = -0.5F;
            float f7 = -Minecraft.getMinecraft().player.rotationYaw / 64.0F;
            float f8 = Minecraft.getMinecraft().player.rotationPitch / 64.0F;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex(4.0F + f7, 4.0F + f8).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex(0.0F + f7, 4.0F + f8).endVertex();
            bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex(0.0F + f7, 0.0F + f8).endVertex();
            bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex(4.0F + f7, 0.0F + f8).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
        } else {
            e.setCanceled(false);
        }
    }

    @SubscribeEvent
    public void onFogColor(EntityViewRenderEvent.FogColors e) {
        if (e.getState().getBlock() == MicrobiologyFluidRegistry.OOZE) {
            e.setRed(0.26F);
            e.setBlue(0.61F);
            e.setGreen(0.54F);
        }
        GlStateManager.setFogDensity(0.00F);
    }
}
