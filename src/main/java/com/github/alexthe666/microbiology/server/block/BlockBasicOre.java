package com.github.alexthe666.microbiology.server.block;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBasicOre extends Block {

    public BlockBasicOre(Material material, SoundType sound, String name, String tool, int toolLevel, float hardness, float resistance, boolean glow) {
        super(material);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(sound);
        this.setTranslationKey("microbiology." + name);
        this.setRegistryName(name);
        this.setHarvestLevel(tool, toolLevel);
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
        if(glow){
            this.setLightLevel(0.5F);
        }
    }
}
