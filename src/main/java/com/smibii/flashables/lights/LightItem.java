package com.smibii.flashables.lights;

import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;

import java.util.List;

public class LightItem {
    private final LightMode mode;
    private final LightColor color;
    private final List<LightPosition> positions;
    private final boolean toggleable;

    public LightItem(
            LightMode mode,
            LightColor color,
            List<LightPosition> positions) {
        this(
                mode,
                color,
                positions,
                false
        );
    }

    public LightItem(
            LightMode mode,
            LightColor color,
            List<LightPosition> positions,
            boolean toggleable
    ) {
        this.mode = mode;
        this.color = color;
        this.positions = positions;
        this.toggleable = toggleable;
    }

    public LightMode getMode() { return mode; }
    public LightColor getColor() { return color; }
    public List<LightPosition> getPositions() { return positions; }
    public boolean isToggleable() { return toggleable; }
}
