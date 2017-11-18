package com.github.alexthe666.microbiology.server.dimension.generation;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPollen extends WorldGenerator {
    private final Block block;
    private final int startRadius;

    public WorldGenPollen(Block blockIn, int startRadiusIn) {
        super(false);
        this.block = blockIn;
        this.startRadius = startRadiusIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (true) {
            label50:
            {
                if (position.getY() > 3) {
                    if (worldIn.isAirBlock(position.down())) {
                        break label50;
                    }
                }

                if (position.getY() <= 3) {
                    return false;
                }

                int i1 = this.startRadius;

                for (int i = 0; i1 >= 0 && i < 3; ++i) {
                    int j = i1 + rand.nextInt(4);
                    int k = i1 + rand.nextInt(4);
                    int l = i1 + rand.nextInt(4);
                    float f = (float) (j + k + l) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l))) {
                        if (blockpos.distanceSq(position) <= (double) (f * f)) {
                            worldIn.setBlockState(blockpos, this.block.getDefaultState(), 4);
                        }
                    }

                    position = position.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), 0 - rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
                }

                return true;
            }
            position = position.down();
        }
    }
}