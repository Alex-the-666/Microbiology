package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockMicrobiologyStairs extends BlockStairs {

    protected BlockMicrobiologyStairs(IBlockState modelState, String name) {
        super(modelState);
        this.setLightOpacity(0);
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
        this.setUnlocalizedName("microbiology." + name);
        this.setRegistryName(name);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
