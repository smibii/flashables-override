package com.smibii.flashables.crafting.recipes;

import com.smibii.flashables.crafting.RecipeSerializers;
import com.smibii.flashables.items.ModItems;
import com.smibii.flashables.util.CraftingUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RecolorFlashlightRecipe extends CustomRecipe {
    public RecolorFlashlightRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        boolean foundFlashlight = false, foundPane = false;
        ItemStack flashlight = ItemStack.EMPTY;
        Integer paneColor = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty()) continue;

            if (s.is(ModItems.FLASHLIGHT.get())) {
                if (foundFlashlight) return false;
                foundFlashlight = true;
                flashlight = s;
            } else if (CraftingUtils.PANE_TO_COLOR.containsKey(s.getItem())) {
                if (foundPane) return false;
                foundPane = true;
                paneColor = CraftingUtils.PANE_TO_COLOR.get(s.getItem());
            } else {
                return false;
            }
        }

        if (!(foundFlashlight && foundPane)) return false;
        int currentColor = CraftingUtils.getFlashlightColor(flashlight);
        return paneColor != null && paneColor != currentColor;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess access) {
        ItemStack flashlight = ItemStack.EMPTY;
        int color = 0;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty()) continue;
            if (s.is(ModItems.FLASHLIGHT.get())) {
                flashlight = s;
            } else {
                Integer c = CraftingUtils.PANE_TO_COLOR.get(s.getItem());
                if (c != null) color = c;
            }
        }

        ItemStack out = new ItemStack(ModItems.FLASHLIGHT.get());
        if (!flashlight.isEmpty() && flashlight.hasTag()) {
            out.setTag(flashlight.getTag().copy());
        }
        out.getOrCreateTag().putInt("color", color);
        return out;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

        ItemStack flashlight = ItemStack.EMPTY;
        int paneSlot = -1;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty()) continue;

            if (s.is(ModItems.FLASHLIGHT.get())) {
                flashlight = s;
            } else if (CraftingUtils.PANE_TO_COLOR.containsKey(s.getItem())) {
                paneSlot = i;
            }
        }

        if (!flashlight.isEmpty() && paneSlot >= 0) {
            int currentColor = CraftingUtils.getFlashlightColor(flashlight);
            Item oldPane = CraftingUtils.COLOR_TO_PANE.get(currentColor);
            if (oldPane != null) {
                remaining.set(paneSlot, new ItemStack(oldPane));
            }
        }

        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) { return w * h >= 2; }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.RECOLOR_FLASHLIGHT.get();
    }
}
