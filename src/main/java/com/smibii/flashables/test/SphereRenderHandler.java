package com.smibii.flashables.test;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smibii.flashables.constants.ModConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// @Mod.EventBusSubscriber(modid = ModConstants.MODID, value = Dist.CLIENT)
public class SphereRenderHandler {

    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) return;
        if (MC.level == null || MC.cameraEntity == null) return;

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = MC.renderBuffers().bufferSource();
        Vec3 camPos = MC.gameRenderer.getMainCamera().getPosition();

        double sphereX = -600;
        double sphereY = 70;
        double sphereZ = -300;
        Vec3 center = new Vec3(sphereX, sphereY, sphereZ);

        poseStack.pushPose();
        poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

        SphereRenderer.SolidSphere.render(poseStack, bufferSource,
                center, 20, 20,
                0x80FF0000
        );

        poseStack.popPose();
        bufferSource.endBatch(RenderType.solid());
    }
}