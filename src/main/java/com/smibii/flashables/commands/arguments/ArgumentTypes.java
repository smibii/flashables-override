package com.smibii.flashables.commands.arguments;

import net.minecraft.commands.synchronization.ArgumentTypeInfos;

public class ArgumentTypes {
    public static void register() {
        ArgumentTypeInfos.registerByClass(LightPositionArgument.class, new LightPositionArgumentInfo());
        ArgumentTypeInfos.registerByClass(LightModeArgument.class, new LightModeArgumentInfo());
        ArgumentTypeInfos.registerByClass(LightColorArgument.class, new LightColorArgumentInfo());
    }
}
