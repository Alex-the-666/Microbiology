package com.github.alexthe666.microbiology.server.item;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class MicrobiologyItemRegistry {

    public static List<Item> ITEMS = new ArrayList<>();

    @GameRegistry.ObjectHolder(Microbiology.MODID + ":infinitum_ingot")
    public static Item INFINITUM_INGOT = new ItemBasic("infinitum_ingot");

}
