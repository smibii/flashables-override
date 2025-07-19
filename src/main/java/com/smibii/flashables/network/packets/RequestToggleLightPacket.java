package com.smibii.flashables.network.packets;

import com.smibii.flashables.accessor.common.PlayerMixinAccessor;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightState;
import com.smibii.flashables.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestToggleLightPacket {
    private final LightPosition position;

    public RequestToggleLightPacket(LightPosition position) {
        this.position = position;
    }

    public static void encode(RequestToggleLightPacket msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.position);
    }

    public static RequestToggleLightPacket decode(FriendlyByteBuf buf) {
        return new RequestToggleLightPacket(buf.readEnum(LightPosition.class));
    }

    public static void handle(RequestToggleLightPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player instanceof PlayerMixinAccessor accessor) {
                LightState state = accessor.get(msg.position);
                state.active = !state.active;

                NetworkHandler.broadcastToAllTracking(
                        new UpdateLightStatePacket(
                                player.getUUID(),
                                msg.position,
                                state
                        ),
                        player
                );
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
