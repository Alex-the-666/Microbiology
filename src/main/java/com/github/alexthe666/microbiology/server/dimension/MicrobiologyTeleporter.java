package com.github.alexthe666.microbiology.server.dimension;

import com.github.alexthe666.microbiology.server.entity.MicrobiologyEntityProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class MicrobiologyTeleporter extends Teleporter {

    boolean shrinking;

    public MicrobiologyTeleporter(WorldServer worldIn, boolean shrinking) {
        super(worldIn);
        this.shrinking = shrinking;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        positionPlayerExiting(entityIn);
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float f) {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long seed) {

    }

    public void positionPlayerExiting(Entity entity){
        if(entity instanceof EntityLivingBase){
            MicrobiologyEntityProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(entity, MicrobiologyEntityProperties.class);
            if(properties != null){
                entity.setLocationAndAngles(properties.lastTeleporterPos.getX() + 0.5F, properties.lastTeleporterPos.getY() + 0.5F, properties.lastTeleporterPos.getZ() + 0.5F, 0F, 0F);
            }
        }
    }

        @Override
    public boolean makePortal(Entity entity) {
        return true;
    }
}