package com.github.alexthe666.microbiology.server.dimension;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.*;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class MicrobiologyWorldData extends WorldSavedData {

    private static final String IDENTIFIER = "microbiology_info";
    private HashMap<Integer, WorldInfoPetriDish> dimensionInfo = new HashMap<Integer, WorldInfoPetriDish>();
    private HashMap<Integer, UUID> removed = new HashMap<Integer, UUID>();

    public MicrobiologyWorldData(String name) {
        super(name);
    }

    public MicrobiologyWorldData() {
        super(IDENTIFIER);
        this.markDirty();
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public int createDimension(WorldInfoPetriDish worldInfo) {
        int dimensionID = getNextId();
        if(!DimensionManager.isDimensionRegistered(dimensionID)){
            dimensionInfo.put(dimensionID, worldInfo);
            DimensionManager.registerDimension(dimensionID, MicrobiologyWorldRegistry.type);
            loadDimension(dimensionID, worldInfo);
        }
        return dimensionID;
    }

    private int getNextId() {
        for(int id = Microbiology.CONFIG.lowerDimensionID; id < Microbiology.CONFIG.higherDimensionID; id++){
            if(!DimensionManager.isDimensionRegistered(id)) {
                return id;
            }
        }
        return 0;
    }

    public static MicrobiologyWorldData get() {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        ISaveHandler saveHandler = server.getActiveAnvilConverter().getSaveLoader(server.getFolderName(), true);
        //File dimensionFile = new File(new File(saveHandler.getWorldDirectory(), "data"), "microbiology");
        MapStorage storage = new MapStorage(saveHandler);
        MicrobiologyWorldData instance = (MicrobiologyWorldData) storage.getOrLoadData(MicrobiologyWorldData.class, IDENTIFIER);
        if (instance == null) {
            instance = new MicrobiologyWorldData();
            storage.setData(IDENTIFIER, instance);
        }
        instance.markDirty();
        return instance;
    }

    public void debug(){
        for (int id : dimensionInfo.keySet()) {
            System.out.println("found dimension ID: " + id);
        }
    }

    public String getDimensionName(int dimensionId) {
        return dimensionInfo.get(dimensionId).getWorldName();
    }

    public HashMap<Integer, WorldInfoPetriDish> getDimensionInfo() {
        return dimensionInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList nbtList = nbt.getTagList("dimensionInfo", 10);

        for (int i = 0; i < nbtList.tagCount(); i++) {
            NBTTagCompound compound = nbtList.getCompoundTagAt(i);

            dimensionInfo.put(compound.getInteger("dimensionID"), new WorldInfoPetriDish(compound.getCompoundTag("worldInfo")));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList nbtList = new NBTTagList();

        for (Entry<Integer, WorldInfoPetriDish> entry : dimensionInfo.entrySet()) {
            NBTTagCompound compound = new NBTTagCompound();

            compound.setInteger("dimensionID", entry.getKey());
            compound.setTag("worldInfo", entry.getValue().cloneNBTCompound(null));

            nbtList.appendTag(compound);
        }

        nbt.setTag("dimensionInfo", nbtList);

        return nbt;
    }

    public void loadDimensions() {
        for (Entry<Integer, WorldInfoPetriDish> entry : dimensionInfo.entrySet()) {
            int dimensionID = entry.getKey();
            WorldInfo worldInfo = entry.getValue();
            if (!DimensionManager.isDimensionRegistered(dimensionID)) {
                DimensionManager.registerDimension(dimensionID, MicrobiologyWorldRegistry.type);
                loadDimension(dimensionID, worldInfo);
            }

        }
    }

    private void loadDimension(int dimensionID, WorldInfo worldInfo) {
        WorldServer overworld = (WorldServer) FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
        if (overworld == null) {
            throw new RuntimeException("No overworld!");
        }
        try {
            DimensionManager.getProviderType(dimensionID);
        } catch (Exception e) {
            System.err.println("Dimension cannot load: " + e.getMessage());
            return;
        }

        MinecraftServer mcServer = overworld.getMinecraftServer();
        ISaveHandler savehandler = overworld.getSaveHandler();
        EnumDifficulty difficulty = mcServer.getEntityWorld().getDifficulty();
        WorldServer world = (WorldServer) (new WorldServer( mcServer, savehandler, worldInfo, dimensionID, mcServer.profiler).init());
        world.addEventListener(new ServerWorldEventHandler(mcServer, world));
        MinecraftForge.EVENT_BUS.post(new WorldEvent.Load(world));

        if (!mcServer.isSinglePlayer()) {
            world.getWorldInfo().setGameType(mcServer.getGameType());
        }

        mcServer.setDifficultyForAllWorlds(difficulty);
    }

    public void deleteDimension(ICommandSender sender, int dimensionID) {
        if (!dimensionInfo.containsKey(dimensionID)) {
            sender.sendMessage(new TextComponentString("Not a Petri Dish Dimension").setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        World worldObj = DimensionManager.getWorld(dimensionID);

        if (worldObj.playerEntities.size() > 0) {
            sender.sendMessage(new TextComponentString("Can't delete a dimension with players inside it").setStyle(new Style().setColor(TextFormatting.RED)));
            return;
        }

        Entity entitySender = sender.getCommandSenderEntity();
        removed.put(dimensionID, entitySender != null ? entitySender.getUniqueID() : null);

        DimensionManager.unloadWorld(dimensionID);
    }

    public void unload(World world, int dimensionID) {
        if (dimensionInfo.containsKey(dimensionID)) {
            WorldInfo worldInfo = dimensionInfo.get(dimensionID);

            DimensionManager.unregisterDimension(dimensionID);
        }

        if (removed.containsKey(dimensionID)) {
            UUID uniqueID = removed.get(dimensionID);

            removed.remove(dimensionID);
            dimensionInfo.remove(dimensionID);

            ((WorldServer) world).flush();
            File dimensionFolder = new File(DimensionManager.getCurrentSaveRootDirectory(), "DIM" + dimensionID);

            EntityPlayerMP player = null;
            if (uniqueID != null) {
                player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uniqueID);
            }

            try {
                FileUtils.deleteDirectory(dimensionFolder);
            } catch (IOException e) {
                e.printStackTrace();
                if (player != null) {
                    player.sendMessage(new TextComponentString("Error deleting dimension folder of " + dimensionID + ". Has to be removed manually.").setStyle(new Style().setColor(TextFormatting.RED)));
                }
            } finally {
                if (player != null) {
                    player.sendMessage(new TextComponentString("Completely deleted dimension " + dimensionID).setStyle(new Style().setColor(TextFormatting.GREEN)));
                }
            }
        }
    }
}
