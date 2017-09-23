package com.github.alexthe666.microbiology.server.block.entity;

import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.block.BlockTeleporter;
import com.github.alexthe666.microbiology.server.block.IMachinePart;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TileEntityTeleporter extends TileEntity implements ITickable, IMachinePart {

    private BlockPos topBlock;
    private Random rand = new Random();
    @Override
    public void update() {
        if(this.isTop()){
            checkUnder(this.getBlockType(), this.world, this.pos);
        }
        if(topBlock != null && world.getBlockState(topBlock).getBlock() instanceof BlockTeleporter && this.getBlockType() == MicrobiologyBlockRegistry.TELEPORTER_ON){
            if(this.isTop()){
                Microbiology.PROXY.createParticle(this.world, "teleporter", this.pos.getX() + rand.nextFloat(), this.pos.getY() + 0.6, this.pos.getZ() + rand.nextFloat(), 0, 0.1D * (this.topBlock.getY() - this.pos.getY()), 0);
            }else{
                Microbiology.PROXY.createParticle(this.world, "teleporter", this.pos.getX() + rand.nextFloat(), this.pos.getY() + 0.6, this.pos.getZ() + rand.nextFloat(), 0, -0.1D * (this.pos.getY() - this.topBlock.getY()), 0);
            }
        }
    }

    public void onBlockUpdate(){
        if(!this.isTop()){
            for(int y = 0; y < 12; y++){
                BlockPos position = pos.up(y);
                IBlockState state = world.getBlockState(position);
                if(this.getBlockType() == MicrobiologyBlockRegistry.TELEPORTER_ON && state.getBlock() == MicrobiologyBlockRegistry.TELEPORTER_OFF){
                    if(world.getTileEntity(position) instanceof TileEntityTeleporter){
                        ((TileEntityTeleporter)world.getTileEntity(position)).activate();
                    }
                    break;
                }
                if(this.getBlockType() == MicrobiologyBlockRegistry.TELEPORTER_OFF && state.getBlock() == MicrobiologyBlockRegistry.TELEPORTER_ON){
                    if(world.getTileEntity(position) instanceof TileEntityTeleporter){
                        ((TileEntityTeleporter)world.getTileEntity(position)).deactivate();
                    }
                    break;
                }
            }
        }
    }

    public static void checkUnder(Block block, World world, BlockPos startPos){
        boolean foundABlock = false;
        for(int y = 1; y < 12; y++){
            BlockPos position = startPos.down(y);
            IBlockState state = world.getBlockState(position);
            if(state.getBlock() instanceof BlockTeleporter){
                foundABlock = true;
            }
            if(block == MicrobiologyBlockRegistry.TELEPORTER_ON && state.getBlock() == MicrobiologyBlockRegistry.TELEPORTER_OFF){
                if(world.getTileEntity(position) instanceof TileEntityTeleporter){
                    EnumFacing facing = world.getBlockState(startPos).getValue(BlockTeleporter.FACING);
                    world.setBlockState(startPos, MicrobiologyBlockRegistry.TELEPORTER_OFF.getDefaultState().withProperty(BlockTeleporter.FACING, facing));
                    ((TileEntityTeleporter)world.getTileEntity(position)).topBlock = startPos;
                    ((TileEntityTeleporter)world.getTileEntity(startPos)).topBlock = position;
                }
                break;
            }
            if(block == MicrobiologyBlockRegistry.TELEPORTER_OFF && state.getBlock() == MicrobiologyBlockRegistry.TELEPORTER_ON){
                if(world.getTileEntity(position) instanceof TileEntityTeleporter){
                    EnumFacing facing = world.getBlockState(startPos).getValue(BlockTeleporter.FACING);
                    world.setBlockState(startPos, MicrobiologyBlockRegistry.TELEPORTER_ON.getDefaultState().withProperty(BlockTeleporter.FACING, facing));
                    ((TileEntityTeleporter)world.getTileEntity(position)).topBlock = startPos;
                    ((TileEntityTeleporter)world.getTileEntity(startPos)).topBlock = position;
                }
                break;
            }
        }
        if(!foundABlock){
            EnumFacing facing = world.getBlockState(startPos).getValue(BlockTeleporter.FACING);
            world.setBlockState(startPos, MicrobiologyBlockRegistry.TELEPORTER_OFF.getDefaultState().withProperty(BlockTeleporter.FACING, facing));
            ((TileEntityTeleporter)world.getTileEntity(startPos)).topBlock = null;

        }
    }

    @Override
    public void activate() {
        EnumFacing facing = world.getBlockState(pos).getValue(BlockTeleporter.FACING);
        world.setBlockState(pos, MicrobiologyBlockRegistry.TELEPORTER_OFF.getDefaultState().withProperty(BlockTeleporter.FACING, facing));
    }

    @Override
    public void deactivate() {
        EnumFacing facing = world.getBlockState(pos).getValue(BlockTeleporter.FACING);
        world.setBlockState(pos, MicrobiologyBlockRegistry.TELEPORTER_ON.getDefaultState().withProperty(BlockTeleporter.FACING, facing));
        onBlockUpdate();
    }

    public boolean isTop(){
        return world.getBlockState(pos).getBlock() instanceof BlockTeleporter && world.getBlockState(pos).getValue(BlockTeleporter.FACING) == EnumFacing.DOWN;
    }
}
