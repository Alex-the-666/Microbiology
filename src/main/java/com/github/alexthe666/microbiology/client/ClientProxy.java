package com.github.alexthe666.microbiology.client;

import com.github.alexthe666.microbiology.client.particle.ParticleTeleporter;
import com.github.alexthe666.microbiology.client.render.tile.RenderMicroscope;
import com.github.alexthe666.microbiology.client.render.tile.RenderTeleporter;
import com.github.alexthe666.microbiology.server.ServerProxy;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityMicroscope;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityTeleporter;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends ServerProxy{


    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){
        for (Block block: MicrobiologyBlockRegistry.BLOCKS){
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
        for (Item item: MicrobiologyItemRegistry.ITEMS){
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicroscope.class, new RenderMicroscope());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporter.class, new RenderTeleporter());

        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.MICROSCOPE_OFF), 0, TileEntityMicroscope.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.MICROSCOPE_ON), 0, TileEntityMicroscope.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.TELEPORTER_OFF), 0, TileEntityTeleporter.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.TELEPORTER_ON), 0, TileEntityTeleporter.class);

    }

    public static void init(){

    }

    public static void postInit(){

    }

    @Override
    public void createParticle(World world, String name, double x, double y, double z, double motX, double motY, double motZ){
        Particle particle = null;
        if(name == "teleporter"){
            particle = new ParticleTeleporter.Factory().createParticle(0, world, x, y, z, motX, motY, motZ, new int[0]);
        }
        if(particle != null){
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        }
    }
}
