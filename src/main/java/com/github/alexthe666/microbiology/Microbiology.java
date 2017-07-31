package com.github.alexthe666.microbiology;

import com.github.alexthe666.microbiology.server.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Microbiology.MODID, name = Microbiology.NAME, dependencies = "required-after:llibrary@[" + Microbiology.LLIBRARY_VERSION + ",)", version = Microbiology.VERSION)
public class Microbiology
{
    public static final String MODID = "microbiology";
    public static final String VERSION = "alpha";
    public static final String LLIBRARY_VERSION = "1.7.6";
    public static final String NAME = "Microbiology";
    @SidedProxy(clientSide = "com.github.alexthe666.microbiology.client.ClientProxy", serverSide = "com.github.alexthe666.microbiology.server.ServerProxy")
    public static ServerProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit();
    }
}
