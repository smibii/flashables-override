package com.smibii.flashables.network.packets;

import com.smibii.flashables.client.logic.ClientLightLogic;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class UpdateLightStatePacket {
    private final UUID playerId;
    private final LightPosition position;
    private final LightState state;

    public UpdateLightStatePacket(UUID playerId, LightPosition position, LightState state) {
        this.playerId = playerId;
        this.position = position;
        this.state = state;
    }

    public static void encode(UpdateLightStatePacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId);
        buf.writeEnum(msg.position);
        buf.writeEnum(msg.state.mode);
        buf.writeEnum(msg.state.color);
        buf.writeBoolean(msg.state.active);
    }

    public static UpdateLightStatePacket decode(FriendlyByteBuf buf) {
        UUID playerId = buf.readUUID();
        LightPosition position = buf.readEnum(LightPosition.class);
        LightMode mode = buf.readEnum(LightMode.class);
        LightColor color = buf.readEnum(LightColor.class);
        boolean active = buf.readBoolean();

        LightState state = new LightState();
        state.mode = mode;
        state.color = color;
        state.active = active;

        return new UpdateLightStatePacket(playerId, position, state);
    }

    public static void handle(UpdateLightStatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LightState state = new LightState();
            state.active = msg.state.active;
            state.color = msg.state.color;
            state.mode = msg.state.mode;

            ClientLightLogic.setState(msg.playerId, msg.position, state);
        });
        ctx.get().setPacketHandled(true);
    }
}
