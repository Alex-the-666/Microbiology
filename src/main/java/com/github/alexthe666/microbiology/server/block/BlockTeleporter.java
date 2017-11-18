package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityTeleporter;
import com.github.alexthe666.microbiology.server.dimension.MicrobiologyTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTeleporter extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.VERTICAL);
    protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 0.5D, 1D);
    protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1D, 1D, 1D);

    public BlockTeleporter(boolean isActive) {
        super(Material.IRON);
        this.setHardness(4.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setUnlocalizedName(isActive ? "microbiology.teleporter_on" : "microbiology.teleporter_off");
        this.setRegistryName(isActive ? "microbiology:teleporter_on" : "microbiology:teleporter_off");
        this.setHarvestLevel("pickaxe", 2);
        if(isActive){
            this.setLightLevel(0.5F);
        }else{
            this.setCreativeTab(Microbiology.CREATIVE_TAB);
        }
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        GameRegistry.registerTileEntity(TileEntityTeleporter.class, "microbiology:teleporter");
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(FACING) == EnumFacing.DOWN ? AABB_DOWN : AABB_UP;
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

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }


    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(worldIn.getTileEntity(pos) instanceof TileEntityTeleporter){
           // ((TileEntityTeleporter)worldIn.getTileEntity(pos)).onBlockUpdate();
        }

        if(this.isTouchingNexus(worldIn, pos) && this == MicrobiologyBlockRegistry.TELEPORTER_OFF && worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING) == EnumFacing.UP){
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.TELEPORTER_ON.getDefaultState().withProperty(BlockTeleporter.FACING, worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING)));
        }
        if(!this.isTouchingNexus(worldIn, pos) && this == MicrobiologyBlockRegistry.TELEPORTER_ON && worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING) == EnumFacing.UP){
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.TELEPORTER_OFF.getDefaultState().withProperty(BlockTeleporter.FACING, worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING)));
        }
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(worldIn.getTileEntity(pos) instanceof TileEntityTeleporter){
            //((TileEntityTeleporter)worldIn.getTileEntity(pos)).onBlockUpdate();
        }
        if (!worldIn.isRemote) {
            if (this == MicrobiologyBlockRegistry.TELEPORTER_ON && !this.isTouchingNexus(worldIn, pos) && worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING) == EnumFacing.UP) {
                worldIn.setBlockState(pos, MicrobiologyBlockRegistry.TELEPORTER_OFF.getDefaultState().withProperty(BlockTeleporter.FACING, worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING)));
            }
            else if (this == MicrobiologyBlockRegistry.TELEPORTER_OFF && this.isTouchingNexus(worldIn, pos) && worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING) == EnumFacing.UP) {
                worldIn.setBlockState(pos, MicrobiologyBlockRegistry.TELEPORTER_ON.getDefaultState().withProperty(BlockTeleporter.FACING, worldIn.getBlockState(pos).getValue(BlockTeleporter.FACING)));
            }
        }
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(worldIn.getTileEntity(pos) instanceof TileEntityTeleporter && this == MicrobiologyBlockRegistry.TELEPORTER_ON && entityIn instanceof EntityPlayer && !worldIn.isRemote){
            TileEntityTeleporter tele = (TileEntityTeleporter)worldIn.getTileEntity(pos);
            int dim = tele.getNexusDimension();
            if(dim != 0){
                if ((!entityIn.isBeingRidden()) && (entityIn.getPassengers().isEmpty()) && (entityIn instanceof EntityPlayerMP)) {
                    EntityPlayerMP thePlayer = (EntityPlayerMP) entityIn;
                    if (thePlayer.timeUntilPortal > 0) {
                        thePlayer.timeUntilPortal = 10;
                    } else if (thePlayer.dimension != dim && thePlayer.mcServer.getWorld(dim) != null) {
                        thePlayer.timeUntilPortal = 10;
                        thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, dim, new MicrobiologyTeleporter(thePlayer.mcServer.getWorld(dim)));
                    } else {
                        thePlayer.timeUntilPortal = 10;
                        thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, 0, new MicrobiologyTeleporter(thePlayer.mcServer.getWorld(0)));
                    }
                }
            }
        }
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            if (enumfacing == EnumFacing.DOWN) {
                enumfacing = EnumFacing.UP;
            }else{
                enumfacing = EnumFacing.DOWN;
            }
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MicrobiologyBlockRegistry.TELEPORTER_OFF);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(MicrobiologyBlockRegistry.TELEPORTER_OFF);
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(MicrobiologyBlockRegistry.TELEPORTER_OFF);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing == EnumFacing.DOWN) {
            facing = EnumFacing.UP;
        }else{
            facing = EnumFacing.DOWN;
        }
        return this.getDefaultState().withProperty(FACING, facing);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing facing = placer.rotationPitch > 0 ? EnumFacing.UP : EnumFacing.DOWN;
        worldIn.setBlockState(pos, state.withProperty(FACING, facing), 2);
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = meta == 0 ? EnumFacing.UP : EnumFacing.DOWN;
        return this.getDefaultState().withProperty(FACING, facing);
    }
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTeleporter();
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

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
        if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityTeleporter){
            TileEntityTeleporter tile = (TileEntityTeleporter)worldIn.getTileEntity(pos);
            if(tile.topBlock != null && worldIn.getBlockState(tile.topBlock).getBlock() instanceof BlockTeleporter && this == MicrobiologyBlockRegistry.TELEPORTER_ON){
                if(tile.isTop()){
                    Microbiology.PROXY.createParticle(worldIn, "teleporter", pos.getX() + rand.nextFloat(), pos.getY() + 0.6, pos.getZ() + rand.nextFloat(), 0, 0.1D * (tile.topBlock.getY() - pos.getY()), 0);
                }else{
                    Microbiology.PROXY.createParticle(worldIn, "teleporter", pos.getX() + rand.nextFloat(), pos.getY() + 0.6, pos.getZ() + rand.nextFloat(), 0, -0.1D * (pos.getY() - tile.topBlock.getY()), 0);
                }
            }
        }

    }

}
