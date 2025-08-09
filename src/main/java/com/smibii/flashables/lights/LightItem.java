package com.smibii.flashables.lights;

import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class LightItem {
    private final CompoundTag compound;
    private final LightMode mode;
    private final LightColor color;
    private final List<LightPosition> positions;
    private final boolean toggleable;

    public LightItem(
            CompoundTag compound,
            LightMode mode,
            LightColor color,
            List<LightPosition> positions,
            boolean toggleable
    ) {
        this.compound = compound;
        this.mode = mode;
        this.color = color;
        this.positions = positions;
        this.toggleable = toggleable;
    }

    public CompoundTag getCompound() {
        return compound;
    }
    public LightMode getMode() {
        return mode;
    }
    public LightColor getColor() {
        return color;
    }
    public List<LightPosition> getPositions() {
        return positions;
    }
    public boolean isToggleable() {
        return toggleable;
    }
}
