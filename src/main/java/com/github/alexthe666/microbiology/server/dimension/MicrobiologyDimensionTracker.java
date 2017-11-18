package com.github.alexthe666.microbiology.server.dimension;

import net.minecraftforge.common.DimensionManager;

import java.util.HashSet;
import java.util.Set;

public class MicrobiologyDimensionTracker {

    private static MicrobiologyDimensionTracker INSTANCE;
    private HashSet<Integer> dimensions;

    public MicrobiologyDimensionTracker() {
        dimensions = new HashSet<Integer>();
    }

    public void cleanUp() {
        for (Integer i : dimensions) {
            if (DimensionManager.isDimensionRegistered(i)) {
                DimensionManager.unregisterDimension(i);
            }
        }
    }

    public static MicrobiologyDimensionTracker instance() {
        if (INSTANCE == null) {
            INSTANCE = new MicrobiologyDimensionTracker();
        }

        return INSTANCE;
    }

    public void onLogin(Set<Integer> dimensions) {
        this.cleanUp();
        this.dimensions = new HashSet<>();
        this.dimensions.addAll(dimensions);
        for (int i : dimensions) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                DimensionManager.registerDimension(i, MicrobiologyWorldRegistry.type);
            }
        }
    }
}
