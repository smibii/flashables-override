package com.smibii.flashables.constants;

import net.minecraft.resources.ResourceLocation;

public class ModConstants {
    public static final String MODID = "flashables";
    public static final String PROTOCOL_VERSION = "1";

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MODID, path);
    }
}
