package com.github.alexthe666.microbiology.server.dimension;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderPetriDish extends WorldProvider {

    public void init() {
        this.biomeProvider = new BiomeProviderSingle(MicrobiologyWorldRegistry.MICROSCOPIC_FRESHWATER_EXPANSE);
    }

    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorMicrobiology(this.world, this.world.getSeed(), this.world.getWorldInfo().isMapFeaturesEnabled(), "");
    }

    public DimensionType getDimensionType() {
        return MicrobiologyWorldRegistry.type;
    }

    public boolean hasSkyLight() {
        return true;
    }

    protected void generateLightBrightnessTable() {
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getCloudRenderer() {
        return (net.minecraftforge.client.IRenderHandler)Microbiology.PROXY.getCloudRenderer();
    }

    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
        return (net.minecraftforge.client.IRenderHandler)Microbiology.PROXY.getSkyRenderer();
    }

}