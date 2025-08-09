package com.smibii.flashables.items;

import com.smibii.flashables.util.EnergyNbt;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnergyItem extends Item {
    public EnergyItem(Properties prop) {
        super(prop);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return EnergyNbt.getPower(stack) < EnergyNbt.getMax(stack);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        int power = EnergyNbt.getPower(stack);
        int max = Math.max(1, EnergyNbt.getMax(stack));
        return Math.round(13.0f * power / max);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        float pct = EnergyNbt.getPower(stack) / (float) Math.max(1, EnergyNbt.getMax(stack));
        return Mth.hsvToRgb(pct * 0.33f, 1.0f, 1.0f);
    }
}
