package com.smibii.flashables.lights;

import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LightItemRegistry {
    private static Map<ItemLike, LightItem> lightableItems = new HashMap<>();

    @SubscribeEvent
    public static void setup(ServerStartingEvent event) {
        register(Items.TORCH, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.SOUL_TORCH, LightMode.GLOW, LightColor.CYAN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.REDSTONE_TORCH, LightMode.GLOW, LightColor.RED, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.GLOWSTONE, LightMode.GLOW, LightColor.YELLOW, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.GLOWSTONE_DUST, LightMode.GLOW, LightColor.YELLOW, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.SEA_LANTERN, LightMode.GLOW, LightColor.WHITE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.MAGMA_BLOCK, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.CRYING_OBSIDIAN, LightMode.GLOW, LightColor.PURPLE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.AMETHYST_CLUSTER, LightMode.GLOW, LightColor.MAGENTA, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.LARGE_AMETHYST_BUD, LightMode.GLOW, LightColor.MAGENTA, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.SHROOMLIGHT, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.OCHRE_FROGLIGHT, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.VERDANT_FROGLIGHT, LightMode.GLOW, LightColor.GREEN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.PEARLESCENT_FROGLIGHT, LightMode.GLOW, LightColor.PINK, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.JACK_O_LANTERN, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.BEACON, LightMode.GLOW, LightColor.LIGHT_BLUE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.END_CRYSTAL, LightMode.GLOW, LightColor.PINK, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.SOUL_CAMPFIRE, LightMode.GLOW, LightColor.CYAN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.CAMPFIRE, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.LANTERN, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.SOUL_LANTERN, LightMode.GLOW, LightColor.CYAN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.GLOW_ITEM_FRAME, LightMode.GLOW, LightColor.DEFAULT, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.GLOW_BERRIES, LightMode.GLOW, LightColor.DEFAULT, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.GLOW_INK_SAC, LightMode.GLOW, LightColor.DEFAULT, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.GLOW_LICHEN, LightMode.GLOW, LightColor.DEFAULT, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.REDSTONE_BLOCK, LightMode.GLOW, LightColor.RED, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.LAVA_BUCKET, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.FIRE_CHARGE, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.TOTEM_OF_UNDYING, LightMode.GLOW, LightColor.GREEN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.ENCHANTED_BOOK, LightMode.GLOW, LightColor.PURPLE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.ENCHANTED_GOLDEN_APPLE, LightMode.GLOW, LightColor.PURPLE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.ENCHANTING_TABLE, LightMode.GLOW, LightColor.PURPLE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.NETHER_STAR, LightMode.GLOW, LightColor.MAGENTA, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.ENDER_PEARL, LightMode.GLOW, LightColor.CYAN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.ENDER_EYE, LightMode.GLOW, LightColor.GREEN, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.DRAGON_EGG, LightMode.GLOW, LightColor.MAGENTA, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.ENDER_CHEST, LightMode.GLOW, LightColor.MAGENTA, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.EXPERIENCE_BOTTLE, LightMode.GLOW, LightColor.YELLOW, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.END_ROD, LightMode.GLOW, LightColor.WHITE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.BLAZE_ROD, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
        register(Items.BLAZE_POWDER, LightMode.GLOW, LightColor.ORANGE, List.of(LightPosition.MAINHAND, LightPosition.OFFHAND));
    }

    public static void register(
            ItemLike item,
            LightMode mode,
            LightColor color,
            List<LightPosition> positions
    ) {
        if (lightableItems.containsKey(item)) return;
        lightableItems.put(
                item,
                new LightItem(
                        mode,
                        color,
                        positions
                )
        );
    }

    public static Map<ItemLike, LightItem> getLightItems() { return lightableItems; }
}
