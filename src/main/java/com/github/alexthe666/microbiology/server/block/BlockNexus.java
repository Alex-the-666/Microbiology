package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.block.entity.TileEntityNexus;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockNexus extends BlockContainer {

    protected BlockNexus(boolean isActive) {
        super(Material.IRON);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setTranslationKey(isActive ? "microbiology.nexus_on" : "microbiology.nexus_off");
        this.setRegistryName(isActive ? "microbiology:nexus_on" : "microbiology:nexus_off");
        this.setHarvestLevel("pickaxe", 2);
        if(isActive){
            this.setLightLevel(0.5F);
        }else{
            this.setCreativeTab(Microbiology.CREATIVE_TAB);
        }
        GameRegistry.registerTileEntity(TileEntityNexus.class, "microbiology:nexus");
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MicrobiologyBlockRegistry.NEXUS_OFF);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(MicrobiologyBlockRegistry.NEXUS_OFF);
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(MicrobiologyBlockRegistry.NEXUS_OFF);
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (active) {
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.NEXUS_ON.getDefaultState(), 3);
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.NEXUS_ON.getDefaultState(), 3);
        }
        else {
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.NEXUS_OFF.getDefaultState(), 3);
            worldIn.setBlockState(pos, MicrobiologyBlockRegistry.NEXUS_OFF.getDefaultState(), 3);
        }

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityNexus();
    }
}
