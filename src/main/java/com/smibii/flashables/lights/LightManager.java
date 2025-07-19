package com.smibii.flashables.lights;

import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.types.LightBeam;
import com.smibii.flashables.lights.types.LightGlow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.*;

public class LightManager {
    private static final Map<UUID, Map<LightPosition, LightType>> ACTIVE_LIGHTS = new HashMap<>();

    public static void updateLights(float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        for (Player player : mc.level.players()) {
            if (!(player instanceof AbstractClientPlayer clientPlayer)) continue;

            UUID uuid = player.getUUID();
            Map<LightPosition, LightType> lightMap = ACTIVE_LIGHTS.computeIfAbsent(uuid, id -> new EnumMap<>(LightPosition.class));

            for (LightPosition pos : LightPosition.values()) {
                LightState state = LightState.get(clientPlayer, pos);

                if (state.active) {
                    applyLight(partialTick, clientPlayer, lightMap, pos, state);
                } else {
                    removeLight(lightMap, pos);
                }
            }

            if (lightMap.isEmpty()) {
                ACTIVE_LIGHTS.remove(uuid);
            }
        }
    }

    private static void applyLight(float partialTick, Player player, Map<LightPosition, LightType> lightMap, LightPosition lightPos, LightState state) {
        LightType existingLight = lightMap.get(lightPos);

        if (existingLight == null || existingLight.getMode() != state.mode) {
            if (existingLight != null) existingLight.remove();

            LightType newLight = createLight(state);
            lightMap.put(lightPos, newLight);
            existingLight = newLight;
        }

        float rotX = player.getXRot();
        float rotY = player.getYRot();
        Vec3 eye = player.getEyePosition(partialTick);

        Vector3f forward = new Vector3f(
                -Mth.sin(rotY * ((float) Math.PI / 180F)) * Mth.cos(rotX * ((float) Math.PI / 180F)),
                -Mth.sin(rotX * ((float) Math.PI / 180F)),
                Mth.cos(rotY * ((float) Math.PI / 180F)) * Mth.cos(rotX * ((float) Math.PI / 180F))
        );
        Vector3f right = new Vector3f(forward).cross(new Vector3f(0, 1, 0)).normalize();

        Vector3f offset = switch (lightPos) {
            case MAINHAND -> new Vector3f((float) eye.x, (float) eye.y, (float) eye.z).add(new Vector3f(right).mul(0.3f));
            case OFFHAND -> new Vector3f((float) eye.x, (float) eye.y, (float) eye.z).add(new Vector3f(right).mul(-0.3f));
            case HEAD -> new Vector3f((float) eye.x, (float) eye.y + 0.3f, (float) eye.z);
        };

        existingLight.setColor(state.color);
        existingLight.tick(offset, new Vec2(rotX, rotY));
    }

    private static void removeLight(Map<LightPosition, LightType> lightMap, LightPosition pos) {
        LightType removed = lightMap.remove(pos);
        if (removed != null) removed.remove();
    }

    public static void clearLight(UUID uuid, LightPosition lightPos) {
        Map<LightPosition, LightType> lights = ACTIVE_LIGHTS.remove(uuid);
        lights.remove(lightPos);
    }

    public static void clearLights(UUID uuid) {
        Map<LightPosition, LightType> lights = ACTIVE_LIGHTS.remove(uuid);
        if (lights != null) {
            lights.values().forEach(LightType::remove);
        }
    }

    public static void clearAll() {
        ACTIVE_LIGHTS.values().forEach(lights -> lights.values().forEach(LightType::remove));
        ACTIVE_LIGHTS.clear();
    }

    private static LightType createLight(LightState state) {
        return switch (state.mode) {
            case BEAM -> new LightBeam(state.color);
            case GLOW -> new LightGlow(state.color);
        };
    }
}