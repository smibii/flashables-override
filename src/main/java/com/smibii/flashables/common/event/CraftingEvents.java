package com.smibii.flashables.common.event;

import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ModConstants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftingEvents {
    private static final Map<String, Integer> PANE_TO_COLOR_INDEX = Map.ofEntries(
            Map.entry("glass_pane", 0),
            Map.entry("white_stained_glass_pane", 1),
            Map.entry("light_gray_stained_glass_pane", 2),
            Map.entry("gray_stained_glass_pane", 3),
            Map.entry("black_stained_glass_pane", 4),
            Map.entry("brown_stained_glass_pane", 5),
            Map.entry("red_stained_glass_pane", 6),
            Map.entry("orange_stained_glass_pane", 7),
            Map.entry("yellow_stained_glass_pane", 8),
            Map.entry("lime_stained_glass_pane", 9),
            Map.entry("green_stained_glass_pane", 10),
            Map.entry("cyan_stained_glass_pane", 11),
            Map.entry("light_blue_stained_glass_pane", 12),
            Map.entry("blue_stained_glass_pane", 13),
            Map.entry("purple_stained_glass_pane", 14),
            Map.entry("magenta_stained_glass_pane", 15),
            Map.entry("pink_stained_glass_pane", 16)
    );

    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent e) {
        if (e.getEntity() == null || e.getEntity().level().isClientSide()) return;

        ItemStack result = e.getCrafting();
        if (result.isEmpty()) return;

        if (!result.is(ModItems.FLASHLIGHT.get())) return;

        int colorIndex = 0;
        ItemStack sourceFlashlight = ItemStack.EMPTY;

        var inv = e.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack in = inv.getItem(i);
            if (in.isEmpty()) continue;

            if (sourceFlashlight.isEmpty() && in.is(ModItems.FLASHLIGHT.get())) {
                sourceFlashlight = in;
            }

            var key = ForgeRegistries.ITEMS.getKey(in.getItem());
            if (key == null) continue;
            Integer idx = PANE_TO_COLOR_INDEX.get(key.getPath());
            if (idx != null) {
                colorIndex = idx;
            }
        }

        if (!sourceFlashlight.isEmpty() && sourceFlashlight.hasTag()) {
            result.setTag(sourceFlashlight.getTag().copy());
        }

        result.getOrCreateTag().putInt("color", colorIndex);
    }
}