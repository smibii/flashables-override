package com.smibii.flashables.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightItem;
import com.smibii.flashables.lights.LightItemRegistry;
import com.smibii.flashables.network.NetworkHandler;
import com.smibii.flashables.network.packets.RequestToggleLightPacket;
import com.smibii.flashables.util.EnergyNbt;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyInputHandler {
    public static final KeyMapping TOGGLE_KEY =
            new KeyMapping("key.flashables.toggle", InputConstants.Type.KEYSYM, InputConstants.KEY_F, "key.categories.flashables");

    private static boolean wasPressedLastTick = false;

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class ClientSetup {
        @SubscribeEvent
        public static void register(RegisterKeyMappingsEvent event) {
            event.register(TOGGLE_KEY);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null || mc.player == null) return;

        boolean isPressedNow = TOGGLE_KEY.isDown();
        if (isPressedNow && !wasPressedLastTick) {
            LocalPlayer player = mc.player;
            if (!tryToggle(player.getItemBySlot(EquipmentSlot.HEAD), LightPosition.HEAD)
                    && !tryToggle(player.getMainHandItem(), LightPosition.MAINHAND)) {
                tryToggle(player.getOffhandItem(), LightPosition.OFFHAND);
            }
        }

        wasPressedLastTick = isPressedNow;
    }

    @SubscribeEvent
    public static void onUseKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isUseItem()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.screen != null) return;

        ItemStack mainhand = player.getMainHandItem();
        LightItem li = LightItemRegistry.findFor(mainhand);
        if (li == null) return;

        boolean canToggle = li.isToggleable()
                && li.getPositions() != null
                && li.getPositions().contains(LightPosition.MAINHAND)
                && EnergyNbt.exists(mainhand)
                && EnergyNbt.getPower(mainhand) <= 0;

        if (EnergyNbt.getPower(mainhand) <= 0) {
            player.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT);
        }

        if (canToggle) {
            player.playSound(SoundEvents.FLINTANDSTEEL_USE);
            NetworkHandler.broadcastToServer(new RequestToggleLightPacket(LightPosition.MAINHAND));
            event.setCanceled(true);
        }
    }

    private static boolean tryToggle(ItemStack stack, LightPosition lightPos) {
        LightItem li = LightItemRegistry.findFor(stack);
        if (li == null) return false;

        boolean canToggle = li.isToggleable()
                && li.getPositions() != null
                && li.getPositions().contains(lightPos)
                && EnergyNbt.exists(stack)
                && EnergyNbt.getPower(stack) > 0;

        if (canToggle) {
            NetworkHandler.broadcastToServer(new RequestToggleLightPacket(lightPos));
        }
        return canToggle;
    }
}