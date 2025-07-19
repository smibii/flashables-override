package com.smibii.flashables.lights;

import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.deferred.light.AreaLight;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3fc;

public class LightType {
    protected AreaLight light;
    protected LightColor color;
    protected float brightness;
    protected Vec3 position;
    protected Vec2 rotation;

    private LightMode mode;
    private boolean orientationLock;

    public LightType(LightMode mode, LightColor color, float distance, float brightness, float angle, Vec2 size) {
        this(mode, color, distance, brightness, angle, size, false);
    }

    public LightType(LightMode mode, LightColor color, float distance, float brightness, float angle, Vec2 size, boolean orientationLock) {
        this.light = new AreaLight();

        VeilRenderSystem.renderer().getDeferredRenderer().getLightRenderer().addLight(light);
        light.setDistance(distance);
        light.setAngle(angle);
        light.setSize(size.x, size.y);

        this.color = color;
        this.brightness = brightness;
        this.mode = mode;
        this.orientationLock = orientationLock;
    }

    public void tick(Vector3fc position, Vec2 rotation) {
        this.position = new Vec3(position.x(), position.y(), position.z());
        this.rotation = rotation;

        light.setPosition(position.x(), position.y(), position.z());
        light.setColor(color.get());
        light.setBrightness(brightness);

        if (!orientationLock) {
            light.setOrientation(
                    new Quaternionf().rotateXYZ(
                            (float) Math.toRadians(-rotation.x),
                            (float) Math.toRadians(rotation.y),
                            0
                    )
            );
        }
    };

    public void remove() {
        VeilRenderSystem.renderer().getDeferredRenderer().getLightRenderer().removeLight(light);
    };

    public void setColor(LightColor color) { this.color = color; }
    public void setBrightness(float brightness) { this.brightness = brightness; }
    public void setPosition(Vec3 position) { this.position = position; }
    public void setRotation(Vec2 rotation) { this.rotation = rotation; }
    public LightColor getColor() { return color; }
    public float getBrightness() { return brightness; }
    public Vec3 getPosition() { return position; }
    public Vec2 getRotation() { return rotation; }
    public LightMode getMode() { return mode; }
}
