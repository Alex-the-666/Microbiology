package com.github.alexthe666.microbiology.server.block.entity;

import com.github.alexthe666.microbiology.server.block.IMachinePart;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.item.ItemPetriDish;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TileEntityNexus extends TileEntity implements ITickable {

    private boolean changedFlag;
    private static Random random = new Random();
    @Override
    public void update() {
        boolean power = world.isBlockPowered(pos);
        boolean hasMicro = getMicro() != null;
        Block currentBlock = world.getBlockState(pos).getBlock();
        if(power && currentBlock == MicrobiologyBlockRegistry.NEXUS_OFF && hasMicro){
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

    public TileEntityMicroscope getMicro(){
        for(EnumFacing side : EnumFacing.values()){
            BlockPos next = pos.offset(side);
            if(world.getTileEntity(next) != null && world.getTileEntity(next) instanceof TileEntityMicroscope){
                return (TileEntityMicroscope)world.getTileEntity(next);
            }
        }
        return null;
    }

    public int getDimension(){
        if(this.getMicro() != null && this.getMicro().getStackInSlot(0) != null && this.getMicro().getStackInSlot(0) != ItemStack.EMPTY && this.getMicro().getStackInSlot(0).getItem() != null && this.getMicro().getStackInSlot(0).getItem() == MicrobiologyItemRegistry.PETRI_DISH){
            ItemStack petri = this.getMicro().getStackInSlot(0);
            return ItemPetriDish.getDimensionFor(petri, this.world);
        }
        return 0;
    }
}
