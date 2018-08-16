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

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":petri_glass")
    public static Block PETRI_GLASS = new BlockBasicOre(Material.GLASS, SoundType.GLASS, "petri_glass", "pickaxe", 6, -1, -1, false).setBlockUnbreakable().setResistance(100000);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":microbial_sediment")
    public static Block MICROBIAL_SEDIMENT = new BlockSediment(Material.GROUND, SoundType.GROUND, "microbial_sediment", "shovel", 0, 0.5F, 0F);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":congealed_microbial_sediment")
    public static Block CONGEALED_MICROBIAL_SEDIMENT = new BlockBasicOre(Material.GROUND, SoundType.GROUND, "congealed_microbial_sediment", "pickaxe", 0, 1.5F, 10F, false);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":congealed_microbial_bricks")
    public static Block CONGEALED_MICROBIAL_BRICKS = new BlockBasicOre(Material.GROUND, SoundType.GROUND, "congealed_microbial_bricks", "pickaxe", 0, 2F, 15F, false);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":congealed_microbial_bricks_stairs")
    public static Block CONGEALED_MICROBIAL_BRICKS_STAIRS = new BlockMicrobiologyStairs(CONGEALED_MICROBIAL_BRICKS.getDefaultState(), "congealed_microbial_bricks_stairs");

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":congealed_microbial_bricks_slab")
    public static BlockMicrobialBricksSlab.Half CONGEALED_MICROBIAL_BRICKS_SLAB = new BlockMicrobialBricksSlab.Half("congealed_microbial_bricks_slab", 2F, 15F, SoundType.GROUND);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":congealed_microbial_bricks_double_slab")
    public static BlockMicrobialBricksSlab.Double CONGEALED_MICROBIAL_BRICKS_DOUBLE_SLAB = new BlockMicrobialBricksSlab.Double("congealed_microbial_bricks_double_slab", 2F, 15F, SoundType.GROUND);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":pollen")
    public static Block POLLEN = new BlockBasicOre(Material.WOOD, SoundType.WOOD, "pollen", "axe", 0, 2F, 0F, false);

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":pollen_planks")
    public static Block POLLEN_PLANKS = new BlockBasicOre(Material.WOOD, SoundType.WOOD, "pollen_planks", "axe", 0, 2F, 0F, false);
}
