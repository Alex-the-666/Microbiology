package com.github.alexthe666.microbiology.server.block;

import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

public abstract class BlockMicrobialBricksSlab extends BlockMicrobiologySlab {
    public BlockMicrobialBricksSlab(String name, float hardness, float resistance, SoundType soundType) {
        super(name, hardness, resistance, soundType, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_SLAB);
    }

    @Override
    public ItemBlock getItemBlock() {
        return new BlockMicrobiologySlabItem(this, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_SLAB, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_DOUBLE_SLAB);
    }

    public static class Double extends BlockMicrobiologySlab {
        public Double(String name, float hardness, float resistance, SoundType soundType) {
            super(name, hardness, resistance, soundType, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_SLAB);
        }

        public boolean isDouble() {
            return true;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new BlockMicrobiologySlabItem(this, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_SLAB, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_DOUBLE_SLAB);
        }
    }

    public static class Half extends BlockMicrobiologySlab {
        public Half(String name, float hardness, float resistance, SoundType soundType) {
            super(name, hardness, resistance, soundType, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_SLAB);
        }

        public boolean isDouble() {
            return false;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new BlockMicrobiologySlabItem(this, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_SLAB, MicrobiologyBlockRegistry.CONGEALED_MICROBIAL_BRICKS_DOUBLE_SLAB);
        }
    }
}
