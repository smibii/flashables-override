package com.smibii.flashables.common.event;

import com.smibii.flashables.Flashables;
import com.smibii.flashables.accessor.common.PlayerMixinAccessor;
import com.smibii.flashables.common.logic.ServerLightLogic;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightItem;
import com.smibii.flashables.lights.LightItemRegistry;
import com.smibii.flashables.lights.LightState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    private static final Map<UUID, Map<LightPosition, ItemStack>> LAST_ITEM = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        ServerPlayer serverPlayer = (ServerPlayer) event.player;

        evaluateLightItem(serverPlayer, serverPlayer.getMainHandItem(), LightPosition.MAINHAND);
        evaluateLightItem(serverPlayer, serverPlayer.getOffhandItem(), LightPosition.OFFHAND);
        evaluateLightItem(serverPlayer, serverPlayer.getItemBySlot(EquipmentSlot.HEAD), LightPosition.HEAD);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        evaluateLightItem(serverPlayer, serverPlayer.getMainHandItem(), LightPosition.MAINHAND);
        evaluateLightItem(serverPlayer, serverPlayer.getOffhandItem(), LightPosition.OFFHAND);
        evaluateLightItem(serverPlayer, serverPlayer.getItemBySlot(EquipmentSlot.HEAD), LightPosition.HEAD);

        ServerLevel level = serverPlayer.serverLevel();
        for (ServerPlayer otherPlayer : level.players()) {
            if (otherPlayer == serverPlayer) continue;
            if (!(otherPlayer instanceof PlayerMixinAccessor accessor)) continue;

            for (LightPosition lightPos: LightPosition.values()) {
                LightState state = accessor.get(lightPos);
                ServerLightLogic.setState(otherPlayer, lightPos, state, serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        ServerLightLogic.removeAllLight(serverPlayer);
        LAST_ITEM.remove(serverPlayer.getUUID());
    }

    private static void evaluateLightItem(ServerPlayer player, ItemStack stack, LightPosition lightPos) {
        Map<LightPosition, ItemStack> lastItems = LAST_ITEM.computeIfAbsent(player.getUUID(), id -> new EnumMap<>(LightPosition.class));
        ItemStack previous = lastItems.get(lightPos);

        if (previous == null) previous = ItemStack.EMPTY;
        if (!stack.isEmpty() && ItemStack.isSameItemSameTags(previous, stack)) return;
        lastItems.put(lightPos, stack.copy());

        LightItem item = LightItemRegistry.getLightItems().get(stack.getItem());

        if (item == null || stack.isEmpty()) {
            ServerLightLogic.removeLight(player, lightPos);
            return;
        }

        LightState state = LightState.get(player, lightPos);
        state.mode = item.getMode();
        state.color = item.getColor();
        state.active = item.getPositions() != null && item.getPositions().contains(lightPos) && !item.isToggleable();

        ServerLightLogic.setState(player, lightPos, state);
    }
}
