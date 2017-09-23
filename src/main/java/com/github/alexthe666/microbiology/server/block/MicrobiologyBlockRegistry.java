package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class MicrobiologyBlockRegistry {

    public static List<Block> BLOCKS = new ArrayList<>();

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":infinitum_ore")
    public static Block INFINITUM_ORE = new BlockBasicOre(Material.ROCK, SoundType.STONE, "infinitum_ore", "pickaxe", 2, 3.0F, 5.0F, true);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":nexus_on")
    public static Block NEXUS_ON = new BlockNexus(true);
    @GameRegistry.ObjectHolder(Microbiology.MODID + ":nexus_off")
    public static Block NEXUS_OFF = new BlockNexus(false);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":microscope_on")
    public static Block MICROSCOPE_ON = new BlockMicroscope(true);
    @GameRegistry.ObjectHolder(Microbiology.MODID + ":microscope_off")
    public static Block MICROSCOPE_OFF = new BlockMicroscope(false);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":teleporter_on")
    public static Block TELEPORTER_ON = new BlockTeleporter(true);
    @GameRegistry.ObjectHolder(Microbiology.MODID + ":teleporter_off")
    public static Block TELEPORTER_OFF = new BlockTeleporter(false);
}
