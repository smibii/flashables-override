package com.smibii.flashables.network.packets;

import com.smibii.flashables.client.logic.ClientLightLogic;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncAllLightsPacket {
    private final Map<UUID, Map<LightPosition, LightState>> data;

    public SyncAllLightsPacket(Map<UUID, Map<LightPosition, LightState>> data) {
        this.data = data;
    }

    public static void encode(SyncAllLightsPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.data.size());
        for (var entry : msg.data.entrySet()) {
            buf.writeUUID(entry.getKey());
            Map<LightPosition, LightState> states = entry.getValue();
            buf.writeVarInt(states.size());
            for (var stateEntry : states.entrySet()) {
                buf.writeEnum(stateEntry.getKey());
                LightState state = stateEntry.getValue();
                buf.writeEnum(state.mode);
                buf.writeEnum(state.color);
                buf.writeBoolean(state.active);
            }
        }
    }

    public static SyncAllLightsPacket decode(FriendlyByteBuf buf) {
        Map<UUID, Map<LightPosition, LightState>> result = new HashMap<>();
        int size = buf.readVarInt();
        for (int i = 0; i < size; i++) {
            UUID uuid = buf.readUUID();
            int innerSize = buf.readVarInt();
            Map<LightPosition, LightState> stateMap = new EnumMap<LightPosition, LightState>(LightPosition.class);
            for (int j = 0; j < innerSize; j++) {
                LightPosition pos = buf.readEnum(LightPosition.class);
                LightMode mode = buf.readEnum(LightMode.class);
                LightColor color = buf.readEnum(LightColor.class);
                boolean active = buf.readBoolean();

                LightState state = new LightState();
                state.mode = mode;
                state.color = color;
                state.active = active;
                stateMap.put(pos, state);
            }
            result.put(uuid, stateMap);
        }
        return new SyncAllLightsPacket(result);
    }

    public static void handle(SyncAllLightsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            for (var entry : msg.data.entrySet()) {
                UUID uuid = entry.getKey();
                for (var stateEntry : entry.getValue().entrySet()) {
                    ClientLightLogic.setState(uuid, stateEntry.getKey(), stateEntry.getValue());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
