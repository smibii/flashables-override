package com.smibii.flashables.network;

import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.network.packets.ClearLightPacket;
import com.smibii.flashables.network.packets.RequestToggleLightPacket;
import com.smibii.flashables.network.packets.SyncAllLightsPacket;
import com.smibii.flashables.network.packets.UpdateLightStatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ModConstants.location("main"),
            () -> ModConstants.PROTOCOL_VERSION,
            ModConstants.PROTOCOL_VERSION::equals,
            ModConstants.PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        INSTANCE.registerMessage(id++, UpdateLightStatePacket.class, UpdateLightStatePacket::encode, UpdateLightStatePacket::decode, UpdateLightStatePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id++, RequestToggleLightPacket.class, RequestToggleLightPacket::encode, RequestToggleLightPacket::decode, RequestToggleLightPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        INSTANCE.registerMessage(id++, SyncAllLightsPacket.class, SyncAllLightsPacket::encode, SyncAllLightsPacket::decode, SyncAllLightsPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id++, ClearLightPacket.class, ClearLightPacket::encode, ClearLightPacket::decode, ClearLightPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static <T> void broadcast(T packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
    public static <T> void broadcastToAllTracking(T packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), packet);
    }
    public static <T> void broadcastTo(T packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static <T> void broadcastToServer(T packet) {
        INSTANCE.sendToServer(packet);
    }
}
