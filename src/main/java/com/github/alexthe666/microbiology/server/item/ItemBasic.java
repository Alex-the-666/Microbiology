package com.github.alexthe666.microbiology.server.item;

import com.github.alexthe666.microbiology.Microbiology;
import net.minecraft.item.Item;

public class ItemBasic extends Item {

    public ItemBasic(String name) {
        this.setTranslationKey( "microbiology." + name);
        this.setCreativeTab(Microbiology.CREATIVE_TAB);
        this.setRegistryName(name);
    }
}
