package com.smibii.flashables.common.logic;

import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightState;
import com.smibii.flashables.network.NetworkHandler;
import com.smibii.flashables.network.packets.UpdateLightStatePacket;
import net.minecraft.server.level.ServerPlayer;

public class ServerLightLogic {
    public static void toggleLight(ServerPlayer player, LightPosition lightPos) {
        toggleLight(player, lightPos, null);
    }
    public static void toggleLight(ServerPlayer player, LightPosition lightPos, ServerPlayer broadcastPlayer) {
        LightState state = LightState.get(player, lightPos);
        boolean active = !state.active;
        setLight(player, lightPos, active, broadcastPlayer);
    }

    public static void setLight(ServerPlayer player, LightPosition lightPos, boolean active) {
        setLight(player, lightPos, active, null);
    }
    public static void setLight(ServerPlayer player, LightPosition lightPos, boolean active, ServerPlayer broadcastPlayer) {
        LightState state = LightState.get(player, lightPos);
        state.active = active;
        LightState.set(player, lightPos, state);
        if (broadcastPlayer == null) syncLightStateToAll(player, lightPos, state);
        else syncLightStateToPlayer(player, lightPos, state, broadcastPlayer);
    }

    public static void setMode(ServerPlayer player, LightPosition lightPos, LightMode mode) {
        setMode(player, lightPos, mode, null);
    }
    public static void setMode(ServerPlayer player, LightPosition lightPos, LightMode mode, ServerPlayer broadcastPlayer) {
        LightState state = LightState.get(player, lightPos);
        state.mode = mode;
        LightState.set(player, lightPos, state);
        if (broadcastPlayer == null) syncLightStateToAll(player, lightPos, state);
        else syncLightStateToPlayer(player, lightPos, state, broadcastPlayer);
    }

    public static void setColor(ServerPlayer player, LightPosition lightPos, LightColor color) {
        setColor(player, lightPos, color, null);
    }
    public static void setColor(ServerPlayer player, LightPosition lightPos, LightColor color, ServerPlayer broadcastPlayer) {
        LightState state = LightState.get(player, lightPos);
        state.color = color;
        LightState.set(player, lightPos, state);
        if (broadcastPlayer == null) syncLightStateToAll(player, lightPos, state);
        else syncLightStateToPlayer(player, lightPos, state, broadcastPlayer);
    }

    public static void setState(ServerPlayer player, LightPosition lightPos, LightState newState) {
        setState(player, lightPos, newState, null);
    }
    public static void setState(ServerPlayer player, LightPosition lightPos, LightState newState, ServerPlayer broadcastPlayer) {
        LightState state = LightState.get(player, lightPos);
        state.active = newState.active;
        state.mode = newState.mode;
        state.color = newState.color;
        LightState.set(player, lightPos, state);
        if (broadcastPlayer == null) syncLightStateToAll(player, lightPos, state);
        else syncLightStateToPlayer(player, lightPos, state, broadcastPlayer);
    }

    public static void removeAllLight(ServerPlayer player) {
        removeAllLight(player, null);
    }
    public static void removeAllLight(ServerPlayer player, ServerPlayer broadcastPlayer) {
        for (LightPosition lightPos : LightPosition.values()) {
            removeLight(player, lightPos, broadcastPlayer);
        }
    }

    public static void removeLight(ServerPlayer player, LightPosition lightPos) {
        removeLight(player, lightPos, null);
    }
    public static void removeLight(ServerPlayer player, LightPosition lightPos, ServerPlayer broadcastPlayer) {
        LightState state = LightState.get(player, lightPos);
        state.active = false;
        state.mode = LightMode.BEAM;
        state.color = LightColor.DEFAULT;
        LightState.set(player, lightPos, state);
        if (broadcastPlayer == null) syncLightStateToAll(player, lightPos, state);
        else syncLightStateToPlayer(player, lightPos, state, broadcastPlayer);
    }

    // Syncs
    public static void syncLightStateToAll(ServerPlayer player, LightPosition lightPos, LightState state) {
        UpdateLightStatePacket packet = new UpdateLightStatePacket(
                player.getUUID(),
                lightPos,
                state
        );
        NetworkHandler.broadcast(packet);
    }

    public static void syncLightStateToPlayer(ServerPlayer player, LightPosition lightPos, LightState state, ServerPlayer broadcastPlayer) {
        UpdateLightStatePacket packet = new UpdateLightStatePacket(
                player.getUUID(),
                lightPos,
                state
        );
        NetworkHandler.broadcastTo(packet, broadcastPlayer);
    }
}
