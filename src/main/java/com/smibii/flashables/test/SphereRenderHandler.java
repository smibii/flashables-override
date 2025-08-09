package com.smibii.flashables.test;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.lights.types.LightGlow;
import foundry.veil.api.client.render.deferred.light.AreaLight;
import foundry.veil.api.quasar.emitters.shape.Sphere;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;
import org.joml.Vector3fc;

// @Mod.EventBusSubscriber(modid = ModConstants.MODID, value = Dist.CLIENT)
public class SphereRenderHandler {
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (MC.level == null || MC.cameraEntity == null) return;
        
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = MC.renderBuffers().bufferSource();
        Vec3 camPos = MC.gameRenderer.getMainCamera().getPosition();

        float sphereX = 0;
        float sphereY = 70;
        float sphereZ = 0;
        Vector3fc dimensions = new Vector3f(5, 5, 5);

        poseStack.pushPose();
        poseStack.translate(-camPos.x + sphereX, -camPos.y + sphereY, -camPos.z + sphereZ);

        Sphere sphere = new Sphere();
        sphere.renderShape(
                poseStack,
                bufferSource.getBuffer(
                        RenderType.create(
                                "debug_filled_box",
                                DefaultVertexFormat.POSITION_COLOR,
                                VertexFormat.Mode.TRIANGLE_STRIP,
                                131072,
                                false,
                                true,
                                RenderType.CompositeState.builder().setShaderState(
                                        new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader)
                                ).setLayeringState(
                                        new RenderStateShard.LayeringStateShard("view_offset_z_layering", () -> {
                                            PoseStack posestack = RenderSystem.getModelViewStack();
                                            posestack.pushPose();
                                            posestack.scale(0.99975586F, 0.99975586F, 0.99975586F);
                                            RenderSystem.applyModelViewMatrix();
                                        }, () -> {
                                            PoseStack posestack = RenderSystem.getModelViewStack();
                                            posestack.popPose();
                                            RenderSystem.applyModelViewMatrix();
                                        })
                                ).setTransparencyState(
                                        new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
                                            RenderSystem.enableBlend();
                                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                                        }, () -> {
                                            RenderSystem.disableBlend();
                                            RenderSystem.defaultBlendFunc();
                                        })
                                ).createCompositeState(false))),
                dimensions,
                new Vector3f(0, 0, 0));

        // SphereRenderer.SolidSphere.render(poseStack, bufferSource,
        //         center, 20, 20,
        //         0x80FF0000
        // );

        poseStack.popPose();
        bufferSource.endBatch(RenderType.solid());
    }
}