package com.smibii.flashables.lights.types;

import com.smibii.flashables.lights.LightType;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import net.minecraft.world.phys.Vec2;

public class LightBeam extends LightType {
    public LightBeam(LightColor color) {
        super(LightMode.BEAM, color, 100f, 0.9f, 0.7f, new Vec2(0.04f,0.04f));
    }
}
