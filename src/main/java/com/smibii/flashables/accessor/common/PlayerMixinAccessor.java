package com.smibii.flashables.accessor.common;

import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightState;

public interface PlayerMixinAccessor {
    LightState get(LightPosition lightPos);
    void set(LightPosition lightPos, LightState state);
}
