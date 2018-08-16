package com.github.alexthe666.microbiology.server.recipe;

import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class MicrobiologyRecipeRegistry {

    public static void init() {
        GameRegistry.addSmelting(MicrobiologyBlockRegistry.INFINITUM_ORE, new ItemStack(MicrobiologyItemRegistry.INFINITUM_INGOT), 2F);
        OreDictionary.registerOre("plankWood", MicrobiologyBlockRegistry.POLLEN_PLANKS);
        OreDictionary.registerOre("logWood", MicrobiologyBlockRegistry.POLLEN);

        /*
                PetriDishItemRegistry.add(Blocks.DIRT, PetriDishItemRegistry.Type.SOIL);
        PetriDishItemRegistry.add(Blocks.GRASS, PetriDishItemRegistry.Type.SOIL);
        PetriDishItemRegistry.add(Blocks.LEAVES, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.LEAVES2, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.TALLGRASS, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.RED_FLOWER, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.YELLOW_FLOWER, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.PUMPKIN, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.DOUBLE_PLANT, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Blocks.DEADBUSH, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.APPLE, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.CARROT, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.WHEAT_SEEDS, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.MELON_SEEDS, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.MELON, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.BEETROOT_SEEDS, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.BEETROOT, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.PUMPKIN_SEEDS, PetriDishItemRegistry.Type.PLANT);
        PetriDishItemRegistry.add(Items.WATER_BUCKET, PetriDishItemRegistry.Type.FRESHWATER);
        PetriDishItemRegistry.add(new ItemStack(MicrobiologyItemRegistry.VIAL, 1, 1), PetriDishItemRegistry.Type.FRESHWATER);
        PetriDishItemRegistry.add(new ItemStack(MicrobiologyItemRegistry.VIAL, 1, 2), PetriDishItemRegistry.Type.SEAWATER);
         */
        OreDictionary.registerOre("microbiologySoil", new ItemStack(Blocks.DIRT, 1, 0));
        OreDictionary.registerOre("microbiologySoil", new ItemStack(Blocks.DIRT, 1, 1));
        OreDictionary.registerOre("microbiologySoil", new ItemStack(Blocks.DIRT, 1, 2));
        OreDictionary.registerOre("microbiologySoil", new ItemStack(Blocks.SAND, 1, 0));
        OreDictionary.registerOre("microbiologySoil", new ItemStack(Blocks.SAND, 1, 1));
        OreDictionary.registerOre("microbiologySoil", Blocks.GRAVEL);
        OreDictionary.registerOre("microbiologySoil", Blocks.GRASS);
        OreDictionary.registerOre("microbiologyPlant", Blocks.LEAVES);
        OreDictionary.registerOre("microbiologyPlant", Blocks.LEAVES2);
        OreDictionary.registerOre("microbiologyPlant", Blocks.TALLGRASS);
        OreDictionary.registerOre("microbiologyPlant", Blocks.RED_FLOWER);
        OreDictionary.registerOre("microbiologyPlant", Blocks.YELLOW_FLOWER);
        OreDictionary.registerOre("microbiologyPlant", Blocks.PUMPKIN);
        OreDictionary.registerOre("microbiologyPlant", Blocks.DOUBLE_PLANT);
        OreDictionary.registerOre("microbiologyPlant", Blocks.DEADBUSH);
        OreDictionary.registerOre("microbiologyPlant", Items.APPLE);
        OreDictionary.registerOre("microbiologyPlant", Items.CARROT);
        OreDictionary.registerOre("microbiologyPlant", Items.WHEAT_SEEDS);
        OreDictionary.registerOre("microbiologyPlant", Items.MELON_SEEDS);
        OreDictionary.registerOre("microbiologyPlant", Items.MELON);
        OreDictionary.registerOre("microbiologyPlant", Items.BEETROOT_SEEDS);
        OreDictionary.registerOre("microbiologyPlant", Items.BEETROOT);
        OreDictionary.registerOre("microbiologyPlant", Items.PUMPKIN_SEEDS);
        OreDictionary.registerOre("microbiologyPlant", Items.PUMPKIN_SEEDS);
        OreDictionary.registerOre("microbiologyFreshwater", new ItemStack(MicrobiologyItemRegistry.VIAL, 1, 1));
        OreDictionary.registerOre("microbiologySaltwater", new ItemStack(MicrobiologyItemRegistry.VIAL, 1, 2));
        OreDictionary.registerOre("microbiologyFreshwater", Items.WATER_BUCKET);

    }

}
