package com.github.alexthe666.microbiology.server.dimension.generation;

import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.dimension.ChunkGeneratorMicrobiology;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class MicrobiologyWorldGenerator implements IWorldGenerator{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int x = (chunkX * 16) + random.nextInt(16);
        int z = (chunkZ * 16) + random.nextInt(16);
        BlockPos surface = world.getHeight(new BlockPos(x, 0, z));
        if(chunkGenerator instanceof ChunkGeneratorMicrobiology){
            if(random.nextInt(40) == 0){
                WorldGenPollen blob = new WorldGenPollen(MicrobiologyBlockRegistry.POLLEN, new Random().nextInt(5) + 1);
                blob.generate(world, random, surface);
            }
        }
    }
}
