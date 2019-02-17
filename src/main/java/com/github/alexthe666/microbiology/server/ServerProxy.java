package com.github.alexthe666.microbiology.server;

import com.github.alexthe666.microbiology.server.block.ISlabItem;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.MicrobiologyFluidRegistry;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyWorldRegistry;
import com.github.alexthe666.microbiology.server.entity.MicrobiologyEntityProperties;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import com.github.alexthe666.microbiology.server.recipe.RecipePetriDish;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class ServerProxy {

    public void preInit(){

    }

    public void init(){
    }

    public void postInit(){

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        MicrobiologyFluidRegistry.register(event);
        try {
            for (Field f : MicrobiologyBlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    event.getRegistry().register((Block) obj);
                    MicrobiologyBlockRegistry.BLOCKS.add((Block) obj);
                } else if (obj instanceof Block[]) {
                    for (Block block : (Block[]) obj) {
                        event.getRegistry().register(block);
                        MicrobiologyBlockRegistry.BLOCKS.add(block);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        for(Block block : MicrobiologyBlockRegistry.BLOCKS){
            ItemBlock itemBlock = null;
            if(block instanceof ISlabItem){
                itemBlock = ((ISlabItem) block).getItemBlock();
            }else{
                itemBlock = new ItemBlock(block);
            }
            itemBlock.setRegistryName(block.getRegistryName());
            event.getRegistry().register(itemBlock);
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IRecipe recipe = new RecipePetriDish();
        recipe.setRegistryName(new ResourceLocation("microbiology:petri_dish_recipe"));
        event.getRegistry().register(recipe);
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(MicrobiologyWorldRegistry.MICROSCOPIC_FRESHWATER_EXPANSE);
    }




    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        try {
            for (Field f : MicrobiologyItemRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    event.getRegistry().register((Item) obj);
                    MicrobiologyItemRegistry.ITEMS.add((Item) obj);
                } else if (obj instanceof Item[]) {
                    for (Item item : (Item[]) obj) {
                        event.getRegistry().register(item);
                        MicrobiologyItemRegistry.ITEMS.add(item);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public void createParticle(World world, String name, double x, double y, double z, double motX, double motY, double motZ){

    }

    public Object getCloudRenderer(){ return null; }
    public Object getSkyRenderer(){ return null; }
}
