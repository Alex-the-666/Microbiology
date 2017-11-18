package com.github.alexthe666.microbiology;

import com.github.alexthe666.microbiology.server.ServerProxy;
import com.github.alexthe666.microbiology.server.dimension.generation.MicrobiologyWorldGenerator;
import com.github.alexthe666.microbiology.server.event.ServerEvents;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import com.github.alexthe666.microbiology.server.recipe.MicrobiologyRecipeRegistry;
import net.ilexiconn.llibrary.server.config.Config;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
            public ItemStack getTabIconItem() {
                return new ItemStack(MicrobiologyItemRegistry.PETRI_DISH);
            }
        };
        PROXY.preInit();
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        GameRegistry.registerWorldGenerator(new MicrobiologyWorldGenerator(), 0);
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
}
