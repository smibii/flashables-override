package com.smibii.flashables.lights;

import com.smibii.flashables.accessor.common.PlayerMixinAccessor;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import net.minecraft.world.entity.player.Player;

public class LightState {
    public boolean active;
    public LightMode mode = LightMode.BEAM;
    public LightColor color;

    public static LightState get(Player player, LightPosition lightPos) {
        if (!(player instanceof PlayerMixinAccessor accessor)) return null;
        return accessor.get(lightPos);
    }

    public static void set(Player player, LightPosition lightPos, LightState state) {
        if (!(player instanceof PlayerMixinAccessor accessor)) return;
        accessor.set(lightPos, state);
    }
}