package com.github.alexthe666.microbiology.server.dimension;


import com.github.alexthe666.microbiology.Microbiology;
import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class MicrobiologyWorldRegistry {

    public static DimensionType type = DimensionType.register("Petri Dish", "petri", 45456, WorldProviderPetriDish.class, false);
    public static Biome MICROSCOPIC_FRESHWATER_EXPANSE = new BiomeMicrobiology("Microscopic Expanse", Microbiology.CONFIG.biomeIDMicroscopicExpanse, -0.3F, 0.1F, 0.8F, MicrobiologyBlockRegistry.MICROBIAL_SEDIMENT.getDefaultState(), MicrobiologyBlockRegistry.MICROBIAL_SEDIMENT.getDefaultState());

    public static void register(){
        BiomeDictionary.addTypes(MICROSCOPIC_FRESHWATER_EXPANSE, BiomeDictionary.Type.WASTELAND);
    }
}
