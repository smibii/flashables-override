package com.smibii.flashables.constants;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public enum LightColor {
    DEFAULT(247f, 203f, 144f),  // 0
    WHITE(255f, 250f, 255f),    // 1
    RED(255f, 0f, 0f),          // 2
    ORANGE(255f, 122f, 0f),     // 3
    YELLOW(255f, 255f, 0f),     // 4
    LIME(0f, 255f, 0f),         // 5
    GREEN(110f, 230f, 130f),    // 6
    CYAN(0f, 255f, 215f),       // 7
    LIGHT_BLUE(0f, 255f, 255f), // 8
    BLUE(0f, 0f, 255f),         // 9
    PURPLE(70f, 0f, 255f),      // 10
    MAGENTA(130f, 0f, 255f),    // 11
    PINK(255f, 0f, 255f);       // 12

    public final float r;
    public final float g;
    public final float b;

    LightColor(float r, float g, float b) {
        this.r = (r / 100) * 100 / 255;
        this.g = (g / 100) * 100 / 255;
        this.b = (b / 100) * 100 / 255;
    }

    public Vector3fc get() {
        return new Vector3f(r, g, b);
    }
}
