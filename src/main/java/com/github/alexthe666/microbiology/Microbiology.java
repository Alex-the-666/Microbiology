package com.github.alexthe666.microbiology;

import com.github.alexthe666.microbiology.server.ServerProxy;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyDimensionTracker;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyWorldData;
import com.github.alexthe666.microbiology.server.dimension.generation.MicrobiologyWorldGenerator;
import com.github.alexthe666.microbiology.server.entity.MicrobiologyEntityProperties;
import com.github.alexthe666.microbiology.server.event.ServerEvents;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import com.github.alexthe666.microbiology.server.recipe.MicrobiologyRecipeRegistry;
import net.ilexiconn.llibrary.server.config.Config;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.server.FMLServerHandler;

@Mod(modid = Microbiology.MODID, name = Microbiology.NAME, dependencies = "required-after:llibrary@[" + Microbiology.LLIBRARY_VERSION + ",)", version = Microbiology.VERSION)
public class Microbiology
{
    public static final String MODID = "microbiology";
    public static final String VERSION = "alpha";
    public static final String LLIBRARY_VERSION = "1.7.7";
    public static final String NAME = "Microbiology";
    @SidedProxy(clientSide = "com.github.alexthe666.microbiology.client.ClientProxy", serverSide = "com.github.alexthe666.microbiology.server.ServerProxy")
    public static ServerProxy PROXY;
    public static CreativeTabs CREATIVE_TAB;
    @SuppressWarnings("deprecation")
    @Config
    public static MicrobiologyConfig CONFIG;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CREATIVE_TAB = new CreativeTabs("microbiology") {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(MicrobiologyItemRegistry.PETRI_DISH);
            }
        };
        PROXY.preInit();
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        GameRegistry.registerWorldGenerator(new MicrobiologyWorldGenerator(), 0);
        EntityPropertiesHandler.INSTANCE.registerProperties(MicrobiologyEntityProperties.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MicrobiologyRecipeRegistry.init();
        PROXY.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit();
    }

    private static boolean loaded = false;
    @EventHandler
    public void serverStarting(FMLServerAboutToStartEvent event) {
        MicrobiologyWorldData data = MicrobiologyWorldData.get();
        data.loadDimensions();
        MicrobiologyDimensionTracker.instance().onLogin(data.getDimensionInfo().keySet());
    }
}
