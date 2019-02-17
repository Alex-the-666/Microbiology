package com.github.alexthe666.microbiology.server.entity;

import jdk.nashorn.internal.ir.Block;
import net.ilexiconn.llibrary.server.entity.EntityProperties;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class MicrobiologyEntityProperties extends EntityProperties<EntityLivingBase> {
    public BlockPos lastTeleporterPos = BlockPos.ORIGIN;

    @Override
    public void init() {

    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger("LastTeleporterX", lastTeleporterPos.getX());
        compound.setInteger("LastTeleporterY", lastTeleporterPos.getY());
        compound.setInteger("LastTeleporterZ", lastTeleporterPos.getZ());
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        lastTeleporterPos = new BlockPos(compound.getInteger("LastTeleporterX"), compound.getInteger("LastTeleporterY"), compound.getInteger("LastTeleporterZ"));
    }

    @Override
    public int getTrackingTime() {
        return 20;
    }

    @Override
    public String getID() {
        return "Microbiology Entity Properties";
    }

    @Override
    public Class<EntityLivingBase> getEntityClass() {
        return EntityLivingBase.class;
    }
}
