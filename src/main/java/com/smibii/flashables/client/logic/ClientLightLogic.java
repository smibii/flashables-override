package com.smibii.flashables.client.logic;

import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightManager;
import com.smibii.flashables.lights.LightState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ClientLightLogic {
    public static void setState(UUID playerUUID, LightPosition lightPos, LightState state) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        Player player = mc.level.getPlayerByUUID(playerUUID);
        if (!(player instanceof AbstractClientPlayer clientPlayer)) return;

        LightState.set(clientPlayer, lightPos, state);
    }

    public static void clear(UUID playerId, LightPosition lightPos) {
        LightManager.clearLight(playerId, lightPos);
    }
}
