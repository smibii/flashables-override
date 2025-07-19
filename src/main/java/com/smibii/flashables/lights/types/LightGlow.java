package com.smibii.flashables.lights.types;

import com.smibii.flashables.lights.LightType;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import net.minecraft.world.phys.Vec2;

public class LightGlow extends LightType {
    public LightGlow(LightColor color) {
        super(LightMode.GLOW, color, 10f, 0.8f, 4f, new Vec2(0.0f,0.0f));
    }
}
