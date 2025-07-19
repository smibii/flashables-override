package com.smibii.flashables.network.packets;

import com.smibii.flashables.client.logic.ClientLightLogic;
import com.smibii.flashables.constants.LightPosition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClearLightPacket {
    private final UUID playerId;
    private final LightPosition lightPos;

    public ClearLightPacket(UUID playerId, LightPosition lightPos) {
        this.playerId = playerId;
        this.lightPos = lightPos;
    }

    public static void encode(ClearLightPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId);
        buf.writeEnum(msg.lightPos);
    }

    public static ClearLightPacket decode(FriendlyByteBuf buf) {
        return new ClearLightPacket(buf.readUUID(), buf.readEnum(LightPosition.class));
    }

    public static void handle(ClearLightPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientLightLogic.clear(msg.playerId, msg.lightPos);
        });
        ctx.get().setPacketHandled(true);
    }
}
