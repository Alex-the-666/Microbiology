package com.github.alexthe666.microbiology.server.event;

import com.github.alexthe666.microbiology.server.dimension.MicrobiologyDimensionTracker;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyWorldData;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ServerEvents {

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        MicrobiologyWorldData.instance().loadDimensions();
        MicrobiologyDimensionTracker.instance().onLogin(MicrobiologyWorldData.instance().getDimensionInfo().keySet());
    }

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload event) {
        int dimensionID = event.getWorld().provider.getDimension();
        if (!event.getWorld().isRemote) {
            MicrobiologyWorldData.instance().unload(event.getWorld(), dimensionID);
        }
    }

    @SubscribeEvent
    public void onMushroomSpawn(DecorateBiomeEvent.Decorate event) {
        if (event.getType() == DecorateBiomeEvent.Decorate.EventType.SHROOM && MicrobiologyWorldData.instance().getDimensionInfo().containsKey(event.getWorld().provider.getDimension())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void clientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (!event.getManager().isLocalChannel()) {
            MicrobiologyDimensionTracker.instance().cleanUp();
        }
    }
}
