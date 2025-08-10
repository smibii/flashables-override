package com.smibii.flashables.items;

import com.smibii.flashables.util.EnergyNbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlashlightItem extends EnergyItem {
    public static final String KEY_CAN_TOGGLE = "CanToggle";

    public FlashlightItem(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack flashlight = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        if (!(otherHand.getItem() instanceof BatteryItem)) {
            return InteractionResultHolder.pass(flashlight);
        }

        int room = EnergyNbt.getMax(flashlight) - EnergyNbt.getPower(flashlight);
        if (room <= 0) return InteractionResultHolder.sidedSuccess(flashlight, level.isClientSide);

        int moved = EnergyNbt.extract(otherHand, room);
        if (moved > 0) {
            EnergyNbt.insert(flashlight, moved);

            CompoundTag tag = flashlight.getOrCreateTag();
            tag.putBoolean(KEY_CAN_TOGGLE, true);

            if (!level.isClientSide) {
                level.playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.EXPERIENCE_ORB_PICKUP,
                        SoundSource.PLAYERS,
                        0.4f, 1.2f);
            }
        }

        if (EnergyNbt.getPower(otherHand) <= 0) {
            otherHand.shrink(1);
            if (otherHand.getCount() >= 1) {
                EnergyNbt.setPower(otherHand, EnergyNbt.DEFAULT_MAX);
            }
        }

        return InteractionResultHolder.sidedSuccess(flashlight, level.isClientSide);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Power: " + EnergyNbt.getPower(stack) + " / " + EnergyNbt.getMax(stack)));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged && ItemStack.isSameItem(oldStack, newStack)) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        if (ItemStack.isSameItem(oldStack, newStack)) {
            return false;
        }
        return super.shouldCauseBlockBreakReset(oldStack, newStack);
    }
}
