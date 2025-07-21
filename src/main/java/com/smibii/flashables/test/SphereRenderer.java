package com.smibii.flashables.test;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SphereRenderer {
    public static class SolidSphere {

        /**
         * Renders a solid sphere at the given world coordinates.
         *
         * @param poseStack    The current pose stack
         * @param bufferSource The buffer source
         * @param center       The position of the center of the sphere
         * @param radius       The radius of the sphere
         * @param segments     How smooth the sphere is (more segments = smoother)
         * @param color        The ARGB color (e.g., 0xFF00FFFF for opaque cyan)
         */
        public static void render(PoseStack poseStack, MultiBufferSource bufferSource, Vec3 center,
                                  float radius, int segments, int color) {
            VertexConsumer buffer = bufferSource.getBuffer(RenderType.solid());

            float alpha = ((color >> 24) & 0xFF) / 255f;
            float red = ((color >> 16) & 0xFF) / 255f;
            float green = ((color >> 8) & 0xFF) / 255f;
            float blue = (color & 0xFF) / 255f;

            poseStack.pushPose();

            poseStack.translate(center.x, center.y, center.z);

            for (int i = 0; i <= segments; i++) {
                float lat0 = (float) Math.PI * (-0.5f + (float) (i - 1) / segments);
                float z0 = Mth.sin(lat0);
                float zr0 = Mth.cos(lat0);

                float lat1 = (float) Math.PI * (-0.5f + (float) i / segments);
                float z1 = Mth.sin(lat1);
                float zr1 = Mth.cos(lat1);

                for (int j = 0; j <= segments; j++) {
                    float lng = 2 * (float) Math.PI * (float) (j - 1) / segments;
                    float x = Mth.cos(lng);
                    float y = Mth.sin(lng);

                    float x0 = x * zr0 * radius;
                    float y0 = y * zr0 * radius;
                    float z0_ = z0 * radius;

                    float x1 = x * zr1 * radius;
                    float y1 = y * zr1 * radius;
                    float z1_ = z1 * radius;

                    buffer.vertex(poseStack.last().pose(), x0, y0, z0_).color(red, green, blue, alpha).endVertex();
                    buffer.vertex(poseStack.last().pose(), x1, y1, z1_).color(red, green, blue, alpha).endVertex();
                }
            }

            poseStack.popPose();
        }
    }
}