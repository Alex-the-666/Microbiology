package com.github.alexthe666.microbiology.server.block;

import net.minecraft.tileentity.TileEntity;

public interface ITileEntity {
    Class<? extends TileEntity> getEntity();
}
