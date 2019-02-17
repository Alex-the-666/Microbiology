package com.github.alexthe666.microbiology.client;


import com.github.alexthe666.microbiology.client.particle.ParticleTeleporter;
import com.github.alexthe666.microbiology.client.render.sky.MicrobiologyCloudRenderer;
import com.github.alexthe666.microbiology.client.render.sky.MicrobiologySkyRenderer;
import com.github.alexthe666.microbiology.client.render.tile.RenderMicroscope;
import com.github.alexthe666.microbiology.client.render.tile.RenderTeleporter;
import com.github.alexthe666.microbiology.server.ServerProxy;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.MicrobiologyFluidRegistry;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityMicroscope;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityTeleporter;
import com.github.alexthe666.microbiology.server.event.ClientEvents;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.CloudRenderer;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;
@Mod.EventBusSubscriber
public class ClientProxy extends ServerProxy{

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){
        final ModelResourceLocation ooze_model = new ModelResourceLocation("microbiology:ooze", "fluid");
        Item oozeItem = Item.getItemFromBlock(MicrobiologyFluidRegistry.OOZE);
        ModelBakery.registerItemVariants(oozeItem);
        ModelLoader.setCustomMeshDefinition(oozeItem, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return ooze_model;
            }
        });
        ModelLoader.setCustomStateMapper(MicrobiologyFluidRegistry.OOZE, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return ooze_model;
            }
        });

        for (Block block: MicrobiologyBlockRegistry.BLOCKS){
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
        for (Item item: MicrobiologyItemRegistry.ITEMS){
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            if(item == MicrobiologyItemRegistry.VIAL){
                ModelBakery.registerItemVariants(MicrobiologyItemRegistry.VIAL, new ModelResourceLocation("microbiology:vial", "inventory"), new ModelResourceLocation("microbiology:vial_freshwater", "inventory"), new ModelResourceLocation("microbiology:vial_seawater", "inventory"));
                ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation("microbiology:vial_freshwater", "inventory"));
                ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation("microbiology:vial_seawater", "inventory"));

            }
        }
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicroscope.class, new RenderMicroscope());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTeleporter.class, new RenderTeleporter());

        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.MICROSCOPE_OFF), 0, TileEntityMicroscope.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.MICROSCOPE_ON), 0, TileEntityMicroscope.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.TELEPORTER_OFF), 0, TileEntityTeleporter.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(MicrobiologyBlockRegistry.TELEPORTER_ON), 0, TileEntityTeleporter.class);

    }

    public static final int[] SOLID_COLORS = new int[]{0X006600, 0X7D6600, 0X464C00, 0X666D00, 0X666D9E, 0X666D6B, 0X669E49, 0X9B9E49, 0X9B5949, 0XC65949, 0X164C6B};
    public static final int[] LIQUID_COLORS = new int[]{0X2276A3, 0X229EA3, 0X22B7A3, 0XDBB7A3, 0X68B7A3, 0X68B78C, 0XA4B7F0, 0XBFE0EF, 0X7C95EF};
    @Override
    public void init(){
        MinecraftForge.EVENT_BUS.register(new ClientEvents());

        Random randomColorSolid = new Random();
        Random randomColorLiquid = new Random();
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                int colorSoil = SOLID_COLORS[4];
                int colorLiquid = LIQUID_COLORS[1];
                if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("ItemFluid", 8)){
                    int meta = stack.getTagCompound().getInteger("ItemFluidData");
                    long seed = Long.parseLong(Item.getIdFromItem(Item.getByNameOrId(stack.getTagCompound().getString("ItemFluid"))) + "" + meta);
                    randomColorLiquid.setSeed(seed);
                    colorLiquid = LIQUID_COLORS[randomColorLiquid.nextInt(LIQUID_COLORS.length - 1)];
                }
                if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("ItemSoil", 8)){
                    int meta = stack.getTagCompound().getInteger("ItemSoilData");
                    long seed = Long.parseLong(Item.getIdFromItem(Item.getByNameOrId(stack.getTagCompound().getString("ItemSoil"))) + "" + meta);
                    randomColorSolid.setSeed(seed);
                    colorSoil = SOLID_COLORS[randomColorSolid.nextInt(SOLID_COLORS.length - 1)];
                }

                return tintIndex == 0 ? -1 : tintIndex == 1 ? colorLiquid : colorSoil;
            }

        }, MicrobiologyItemRegistry.PETRI_DISH);
    }

    public void postInit(){
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

    public Object getCloudRenderer(){
        return new MicrobiologyCloudRenderer();
    }

    public Object getSkyRenderer(){ return new MicrobiologySkyRenderer(); }

}
