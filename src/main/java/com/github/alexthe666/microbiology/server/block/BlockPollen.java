package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPollen extends Block {

    public BlockPollen() {
        super(Material.WOOD);
        this.setHardness(2F);
        this.setResistance(5F);
        this.setSoundType(SoundType.WOOD);
        this.setUnlocalizedName("microbiology.pollen");
        this.setRegistryName("pollen");
        this.setHarvestLevel("axe", 0);
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
