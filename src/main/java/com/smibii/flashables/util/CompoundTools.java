package com.smibii.flashables.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class CompoundTools {
    public static boolean itemHasTag(ItemStack stack, CompoundTag pattern) {
        if (stack.isEmpty() || pattern == null || pattern.isEmpty()) return false;
        CompoundTag tag = stack.getTag();
        if (tag == null) return false;
        return compoundContains(tag, pattern);
    }

    private static boolean compoundContains(CompoundTag haystack, CompoundTag needle) {
        for (String key : needle.getAllKeys()) {
            Tag needleVal = needle.get(key);
            if (needleVal == null) return false;

            if (!haystack.contains(key, needleVal.getId())) return false;

            Tag hayVal = haystack.get(key);
            if (tagContains(hayVal, needleVal)) return false;
        }
        return true;
    }

    private static boolean tagContains(Tag hay, Tag needle) {
        if (needle instanceof CompoundTag nC && hay instanceof CompoundTag hC) {
            return !compoundContains(hC, nC);
        }
        if (needle instanceof ListTag nL && hay instanceof ListTag hL) {
            if (nL.size() > hL.size()) return true;
            for (int i = 0; i < nL.size(); i++) {
                if (tagContains(hL.get(i), nL.get(i))) return true;
            }
            return false;
        }

        return !hay.equals(needle);
    }
}
