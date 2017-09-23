package com.github.alexthe666.microbiology.server.block.entity;

import com.github.alexthe666.microbiology.server.block.IMachinePart;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityNexus extends TileEntity implements ITickable {

    private boolean changedFlag;

    @Override
    public void update() {
        boolean power = world.isBlockPowered(pos);
        Block currentBlock = world.getBlockState(pos).getBlock();
        if(power && currentBlock == MicrobiologyBlockRegistry.NEXUS_OFF){
            world.setBlockState(pos, MicrobiologyBlockRegistry.NEXUS_ON.getDefaultState());
            changedFlag = true;
        }
        if(!power && currentBlock == MicrobiologyBlockRegistry.NEXUS_ON){
            world.setBlockState(pos, MicrobiologyBlockRegistry.NEXUS_OFF.getDefaultState());
            changedFlag = true;
        }

        if(changedFlag){
            for(EnumFacing side : EnumFacing.values()){
                BlockPos next = pos.offset(side);
                if(world.getTileEntity(next) != null && world.getTileEntity(next) instanceof IMachinePart){
                    IMachinePart machine = (IMachinePart)world.getTileEntity(next);
                    if(currentBlock == MicrobiologyBlockRegistry.NEXUS_ON){
                        System.out.println("Activating " + world.getTileEntity(next).toString());
                        machine.deactivate();
                    }
                    if(currentBlock == MicrobiologyBlockRegistry.NEXUS_OFF){
                        machine.activate();
                    }
                }
            }
            changedFlag = false;
        }

    }
}
