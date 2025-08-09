package com.smibii.flashables.common.event;

import com.smibii.flashables.accessor.common.PlayerMixinAccessor;
import com.smibii.flashables.common.logic.ServerLightLogic;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.lights.LightItem;
import com.smibii.flashables.lights.LightItemRegistry;
import com.smibii.flashables.lights.LightState;
import com.smibii.flashables.util.EnergyNbt;
import net.minecraft.nbt.CompoundTag;
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

    private static final int DRAIN_INTERVAL_TICKS = 20;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        ServerPlayer sp = (ServerPlayer) event.player;

        evaluateLightItem(sp, sp.getMainHandItem(), LightPosition.MAINHAND);
        evaluateLightItem(sp, sp.getOffhandItem(), LightPosition.OFFHAND);
        evaluateLightItem(sp, sp.getItemBySlot(EquipmentSlot.HEAD), LightPosition.HEAD);

        tickCounter++;
        if (tickCounter >= DRAIN_INTERVAL_TICKS) {
            tickCounter = 0;
            drainIfActive(sp, sp.getMainHandItem(), LightPosition.MAINHAND);
            drainIfActive(sp, sp.getOffhandItem(), LightPosition.OFFHAND);
            drainIfActive(sp, sp.getItemBySlot(EquipmentSlot.HEAD), LightPosition.HEAD);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;

        evaluateLightItem(sp, sp.getMainHandItem(), LightPosition.MAINHAND);
        evaluateLightItem(sp, sp.getOffhandItem(), LightPosition.OFFHAND);
        evaluateLightItem(sp, sp.getItemBySlot(EquipmentSlot.HEAD), LightPosition.HEAD);

        ServerLevel level = sp.serverLevel();
        for (ServerPlayer other : level.players()) {
            if (other == sp) continue;
            if (!(other instanceof PlayerMixinAccessor accessor)) continue;

            for (LightPosition pos : LightPosition.values()) {
                LightState state = accessor.get(pos);
                ServerLightLogic.setState(other, pos, state, sp);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        ServerLightLogic.removeAllLight(sp);
        LAST_ITEM.remove(sp.getUUID());
    }

    private static void evaluateLightItem(ServerPlayer player, ItemStack stack, LightPosition pos) {
        Map<LightPosition, ItemStack> last = LAST_ITEM.computeIfAbsent(player.getUUID(), id -> new EnumMap<>(LightPosition.class));
        ItemStack prev = last.getOrDefault(pos, ItemStack.EMPTY);

        if (!stack.isEmpty() && ItemStack.isSameItemSameTags(prev, stack)) return;
        last.put(pos, stack.copy());

        LightItem li = LightItemRegistry.findFor(stack);

        if (li == null) {
            ServerLightLogic.removeLight(player, pos);
            return;
        }

        LightState state = LightState.get(player, pos);

        if (EnergyNbt.exists(stack) && EnergyNbt.getPower(stack) <= 0) {
            state.active = false;
            ServerLightLogic.setState(player, pos, state);
            return;
        }

        state.active = li.getPositions() != null && li.getPositions().contains(pos) && !li.isToggleable();
        state.mode   = li.getMode();
        state.color  = li.getColor();

        ServerLightLogic.setState(player, pos, state);
    }

    private static void drainIfActive(ServerPlayer player, ItemStack stack, LightPosition pos) {
        if (stack.isEmpty() || !EnergyNbt.exists(stack)) return;

        LightState state = LightState.get(player, pos);
        if (!state.active) return;

        int power = EnergyNbt.getPower(stack);
        if (power > 0) {
            EnergyNbt.setPower(stack, power - 1);
            LAST_ITEM.get(player.getUUID()).put(pos, stack);
        }

        if (power - 1 <= 0) {
            state.active = false;
            ServerLightLogic.setState(player, pos, state);
        }
    }
}