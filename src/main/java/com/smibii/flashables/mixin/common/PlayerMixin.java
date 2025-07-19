package com.smibii.flashables.mixin.common;

import com.smibii.flashables.accessor.common.PlayerMixinAccessor;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.EnumMap;
import java.util.Map;

@Mixin(Player.class)
public class PlayerMixin implements PlayerMixinAccessor {
    @Unique
    private final Map<LightPosition, LightState> lights = new EnumMap<>(LightPosition.class);

    @Unique
    public LightState get(LightPosition lightPos) {
        return lights.computeIfAbsent(lightPos, p -> new LightState());
    }

    @Unique
    public void set(LightPosition lightPos, LightState state) {
        this.lights.put(lightPos, state);
    }
}
