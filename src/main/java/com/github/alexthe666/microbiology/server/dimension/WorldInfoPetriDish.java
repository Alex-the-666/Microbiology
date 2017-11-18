package com.github.alexthe666.microbiology.server.dimension;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldInfoPetriDish extends WorldInfo {
    private WorldInfo worldInfo;

    public WorldInfoPetriDish(NBTTagCompound nbt) {
        super(nbt);
        worldInfo = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getWorldInfo();
    }

    public WorldInfoPetriDish(WorldSettings settings, String name) {
        super(settings, name);
    }

    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return worldInfo.getPlayerNBTTagCompound();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public long getLastTimePlayed() {
        return worldInfo.getLastTimePlayed();
    }

    @Override
    public GameType getGameType() {
        return worldInfo.getGameType();
    }

    @Override
    public boolean isHardcoreModeEnabled() {
        return worldInfo.isHardcoreModeEnabled();
    }

    @Override
    public boolean areCommandsAllowed() {
        return worldInfo.areCommandsAllowed();
    }

    @Override
    public GameRules getGameRulesInstance() {
        return worldInfo.getGameRulesInstance();
    }

    @Override
    public EnumDifficulty getDifficulty() {
        return worldInfo.getDifficulty();
    }

    @Override
    public boolean isDifficultyLocked() {
        return worldInfo.isDifficultyLocked();
    }
}
