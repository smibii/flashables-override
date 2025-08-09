package com.smibii.flashables.constants;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public enum LightColor {
    DEFAULT(247f, 203f, 144f),      // 0
    WHITE(255f, 255f, 255f),        // 1
    LIGHT_GRAY(180f, 180f, 180f),   // 2
    GRAY(80f, 80f, 80f),            // 3
    BLACK(120f, 0f, 255f),          // 4
    BROWN(230f, 70f, 0f),           // 5
    RED(255f, 0f, 0f),              // 6
    ORANGE(255f, 122f, 0f),         // 7
    YELLOW(255f, 255f, 0f),         // 8
    LIME(0f, 255f, 0f),             // 9
    GREEN(110f, 230f, 130f),        // 10
    CYAN(0f, 255f, 215f),           // 11
    LIGHT_BLUE(0f, 255f, 255f),     // 12
    BLUE(0f, 0f, 255f),             // 13
    PURPLE(70f, 0f, 255f),          // 14
    MAGENTA(130f, 0f, 255f),        // 15
    PINK(255f, 0f, 255f);           // 16

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
