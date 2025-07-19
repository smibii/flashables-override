package com.smibii.flashables.client.event;

import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightItem;
import com.smibii.flashables.lights.LightItemRegistry;
import com.smibii.flashables.network.NetworkHandler;
import com.smibii.flashables.network.packets.RequestToggleLightPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyInputHandler {
    public static final KeyMapping toggleFlashlightKey = new KeyMapping(
            "key.flashables.toggle",
            GLFW.GLFW_KEY_F,
            "key.categories.flashables"
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(toggleFlashlightKey);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.screen != null || mc.player == null) return;

        LocalPlayer player = mc.player;

        if (tryToggle(player.getItemBySlot(EquipmentSlot.HEAD).getItem(), LightPosition.HEAD)) return;
        if (tryToggle(player.getMainHandItem().getItem(), LightPosition.MAINHAND)) return;
        if (tryToggle(player.getOffhandItem().getItem(), LightPosition.OFFHAND)) return;
    }

    private static boolean tryToggle(ItemLike item, LightPosition lightPos) {
        LightItem lightItem = LightItemRegistry.getLightItems().get(item);
        if (lightItem == null) return false;

        boolean canToggle = lightItem.isToggleable() && lightItem.getPositions().contains(lightPos);
        if (canToggle) {
            NetworkHandler.broadcastToServer(new RequestToggleLightPacket(lightPos));
        }

        return canToggle;
    }
}
