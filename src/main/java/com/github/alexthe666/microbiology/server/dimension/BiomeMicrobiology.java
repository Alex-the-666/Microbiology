package com.github.alexthe666.microbiology.server.dimension;

import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.block.MicrobiologyFluidRegistry;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeMicrobiology extends Biome {

    protected static final IBlockState CONGEALED_SEDIMENT = MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_SEDIMENT.getDefaultState();
    protected static final IBlockState OOZE = MicrobiologyFluidRegistry.OOZE.getDefaultState();
    protected static final IBlockState PETRIGLASS = MicrobiologyBlockRegistry.PETRI_GLASS.getDefaultState();

    public BiomeMicrobiology(String name, int id, float baseHeight, float heightVariation, float temperature, IBlockState topBlock, IBlockState lowerBlock) {
        super(new BiomeProperties(name).setRainDisabled().setBaseHeight(baseHeight).setHeightVariation(heightVariation).setTemperature(temperature));
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.topBlock = topBlock;
        this.decorator.mushroomsPerChunk = 0;
        this.fillerBlock = lowerBlock;
        this.setRegistryName(name);
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        this.generateMicroBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public void generateMicroBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        int i = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int l = x & 15;
        int i1 = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j1 = 255; j1 >= 0; --j1) {
            if (j1 <= 5 || (worldIn.getWorldBorder().minX() + 20 > i1 || worldIn.getWorldBorder().maxX() - 20 < i1 || (worldIn.getWorldBorder().minZ() + 20 > l || worldIn.getWorldBorder().maxZ() - 20 < l))) {
                chunkPrimerIn.setBlockState(i1, j1, l, PETRIGLASS);
            }else {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                if (iblockstate2.getMaterial() == Material.AIR) {
                    j = -1;
                } else if (iblockstate2.getBlock() == MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_SEDIMENT) {
                    if (j == -1) {
                        if (k <= 0) {
                            iblockstate = AIR;
                            iblockstate1 = CONGEALED_SEDIMENT;
                        } else if (j1 >= i - 4 && j1 <= i + 1) {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                            if (this.getFloatTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
                                iblockstate = ICE;
                            } else {
                                iblockstate = OOZE;
                            }
                        }

                        j = k;

                        if (j1 >= i - 1) {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                        } else if (j1 < i - 7 - k) {
                            iblockstate = AIR;
                            iblockstate1 = CONGEALED_SEDIMENT;
                            chunkPrimerIn.setBlockState(i1, j1, l, CONGEALED_SEDIMENT);
                        } else {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                        }
                    } else if (j > 0) {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

                        if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1) {
                            j = rand.nextInt(4) + Math.max(0, j1 - 63);
                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
                        }
                    }
                }
            }
        }
    }
}
