package com.github.alexthe666.microbiology.server.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class MicrobiologyTeleporter extends Teleporter {

    public MicrobiologyTeleporter(WorldServer worldIn) {
        super(worldIn);
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {}

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float f) {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long seed) {

    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }
}