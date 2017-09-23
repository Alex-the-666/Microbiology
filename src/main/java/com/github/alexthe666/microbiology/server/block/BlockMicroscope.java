package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityMicroscope;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMicroscope extends BlockContainer {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.25D, 0.9375D);

    protected BlockMicroscope(boolean isActive) {
        super(Material.IRON);
        this.setHardness(3.0F);
        this.setResistance(7.0F);
        this.setSoundType(SoundType.METAL);
        this.setUnlocalizedName(isActive ? "microbiology.microscope_on" : "microbiology.microscope_off");
        this.setRegistryName(isActive ? "microbiology:microscope_on" : "microbiology:microscope_off");
        this.setHarvestLevel("pickaxe", 2);
        if(isActive){
            this.setLightLevel(0.5F);
        }else{
            this.setCreativeTab(Microbiology.CREATIVE_TAB);
        }
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        GameRegistry.registerTileEntity(TileEntityMicroscope.class, "microbiology:microscope");
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState down = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && down.isOpaqueCube();
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this) {
            IBlockState down = worldIn.getBlockState(pos.down());
            return down.isOpaqueCube();
        }
        return worldIn.getBlockState(pos.down()).isOpaqueCube();
    }


    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }


    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
        if(this.isTouchingNexus(worldIn, pos) && this == MicrobiologyBlockRegistry.MICROSCOPE_OFF){
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_ON.getDefaultState().withProperty(BlockMicroscope.FACING, worldIn.getBlockState(pos).getValue(BlockMicroscope.FACING)));
        }
        if(!this.isTouchingNexus(worldIn, pos) && this == MicrobiologyBlockRegistry.MICROSCOPE_ON){
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_OFF.getDefaultState().withProperty(BlockMicroscope.FACING, worldIn.getBlockState(pos).getValue(BlockMicroscope.FACING)));
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        this.checkAndDropBlock(worldIn, pos, state);
        if (!worldIn.isRemote) {
            if (this == MicrobiologyBlockRegistry.MICROSCOPE_ON && !this.isTouchingNexus(worldIn, pos)) {
                worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_OFF.getDefaultState().withProperty(BlockMicroscope.FACING, worldIn.getBlockState(pos).getValue(BlockMicroscope.FACING)));
            }
        }
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.checkAndDropBlock(worldIn, pos, state);
        if (!worldIn.isRemote) {
            if (this == MicrobiologyBlockRegistry.MICROSCOPE_ON && !this.isTouchingNexus(worldIn, pos)) {
                worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_OFF.getDefaultState().withProperty(BlockMicroscope.FACING, worldIn.getBlockState(pos).getValue(BlockMicroscope.FACING)));
            }
            else if (this == MicrobiologyBlockRegistry.MICROSCOPE_OFF && this.isTouchingNexus(worldIn, pos)) {
                worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_ON.getDefaultState().withProperty(BlockMicroscope.FACING, worldIn.getBlockState(pos).getValue(BlockMicroscope.FACING)));
            }
        }
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }



    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MicrobiologyBlockRegistry.MICROSCOPE_OFF);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(MicrobiologyBlockRegistry.MICROSCOPE_OFF);
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(MicrobiologyBlockRegistry.MICROSCOPE_OFF);
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (active) {
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_ON.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)));
        }
        else {
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.MICROSCOPE_OFF.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)));
        }
        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMicroscope();
    }

    private boolean isTouchingNexus(World world, BlockPos pos){
        for(EnumFacing side : EnumFacing.values()) {
            BlockPos next = pos.offset(side);
            if(world.getBlockState(next).getBlock() == MicrobiologyBlockRegistry.NEXUS_ON){
                return true;
            }
        }
        return false;
    }
}
