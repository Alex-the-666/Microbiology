package com.github.alexthe666.microbiology.server.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MicrobiologyFluidRegistry {

    public static Fluid OOZE_FLUID;
    public static Block OOZE;

    public static void register(RegistryEvent.Register<Block> event) {
        OOZE_FLUID = new Fluid("ooze", new ResourceLocation("microbiology", "blocks/ooze"), new ResourceLocation("microbiology", "blocks/ooze"));
        FluidRegistry.registerFluid(OOZE_FLUID);
        FluidRegistry.addBucketForFluid(OOZE_FLUID);
        OOZE = new BlockOoze(OOZE_FLUID, Material.WATER).setUnlocalizedName("microbiology.ooze");
        event.getRegistry().register(OOZE);
    }
}