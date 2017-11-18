package com.github.alexthe666.microbiology;

import net.ilexiconn.llibrary.server.config.ConfigEntry;

public class MicrobiologyConfig {
    @SuppressWarnings("deprecation")
    @ConfigEntry(category = "IDs", comment = "lower dimension ID limit", minValue = "3")
    public int lowerDimensionID = 3000;

    @SuppressWarnings("deprecation")
    @ConfigEntry(category = "IDs", comment = "higher dimension ID limit", minValue = "6")
    public int higherDimensionID = 4500;

    @SuppressWarnings("deprecation")
    @ConfigEntry(category = "IDs", comment = "lower dimension ID limit", minValue = "3")
    public int biomeIDMicroscopicExpanse = 67;

}
