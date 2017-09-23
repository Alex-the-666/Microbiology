package com.github.alexthe666.microbiology.server.block;

import net.minecraft.item.ItemBlock;

public interface IBlockItem {
    public Class<? extends ItemBlock> getItemBlockClass();
}

