package com.github.alexthe666.microbiology.server.recipe;

import com.github.alexthe666.microbiology.server.block.MicrobiologyBlockRegistry;
import com.github.alexthe666.microbiology.server.item.MicrobiologyItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MicrobiologyRecipeRegistry {

    public static void init() {
        GameRegistry.addSmelting(MicrobiologyBlockRegistry.INFINITUM_ORE, new ItemStack(MicrobiologyItemRegistry.INFINITUM_INGOT), 2F);
    }
}
