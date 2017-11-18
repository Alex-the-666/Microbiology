package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSediment extends Block {

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockSediment(Material material, SoundType sound, String name, String tool, int toolLevel, float hardness, float resistance) {
        super(material);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(sound);
        this.setUnlocalizedName("microbiology." + name);
        this.setRegistryName(name);
        this.setHarvestLevel(tool, toolLevel);
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX *= 0.4D;
        entityIn.motionZ *= 0.4D;
    }
}
