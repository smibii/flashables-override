package com.smibii.flashables.lights;

import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.items.ModItems;
import com.smibii.flashables.util.CompoundTools;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LightItemRegistry {
    private static final Map<Item, List<LightItem>> REGISTRY = new HashMap<>();

    private static final List<LightPosition> POS_BOTH = List.of(LightPosition.MAINHAND, LightPosition.OFFHAND);
    private static final List<LightPosition> POS_HEAD = List.of(LightPosition.HEAD);

    @SubscribeEvent
    public static void setup(ServerStartingEvent event) {
        register(Items.TORCH,           LightMode.GLOW, LightColor.ORANGE, POS_BOTH);
        register(Items.SOUL_TORCH,      LightMode.GLOW, LightColor.CYAN,   POS_BOTH);
        register(Items.REDSTONE_TORCH,  LightMode.GLOW, LightColor.RED,    POS_BOTH);
        register(Items.GLOWSTONE,       LightMode.GLOW, LightColor.YELLOW, POS_BOTH);
        register(Items.GLOWSTONE_DUST,  LightMode.GLOW, LightColor.YELLOW, POS_BOTH);
        register(Items.SEA_LANTERN,     LightMode.GLOW, LightColor.WHITE,  POS_BOTH);
        register(Items.MAGMA_BLOCK,     LightMode.GLOW, LightColor.ORANGE, POS_BOTH);
        register(Items.CRYING_OBSIDIAN, LightMode.GLOW, LightColor.PURPLE, POS_BOTH);
        register(Items.AMETHYST_CLUSTER,     LightMode.GLOW, LightColor.MAGENTA, POS_BOTH);
        register(Items.LARGE_AMETHYST_BUD,   LightMode.GLOW, LightColor.MAGENTA, POS_BOTH);
        register(Items.SHROOMLIGHT,          LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.OCHRE_FROGLIGHT,      LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.VERDANT_FROGLIGHT,    LightMode.GLOW, LightColor.GREEN,   POS_BOTH);
        register(Items.PEARLESCENT_FROGLIGHT,LightMode.GLOW, LightColor.PINK,    POS_BOTH);
        register(Items.JACK_O_LANTERN,       LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.BEACON,               LightMode.GLOW, LightColor.LIGHT_BLUE, POS_BOTH);
        register(Items.END_CRYSTAL,          LightMode.GLOW, LightColor.PINK,    POS_BOTH);
        register(Items.SOUL_CAMPFIRE,        LightMode.GLOW, LightColor.CYAN,    POS_BOTH);
        register(Items.CAMPFIRE,             LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.LANTERN,              LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.SOUL_LANTERN,         LightMode.GLOW, LightColor.CYAN,    POS_BOTH);
        register(Items.GLOW_ITEM_FRAME,      LightMode.GLOW, LightColor.DEFAULT, POS_BOTH);
        register(Items.GLOW_BERRIES,         LightMode.GLOW, LightColor.DEFAULT, POS_BOTH);
        register(Items.GLOW_INK_SAC,         LightMode.GLOW, LightColor.DEFAULT, POS_BOTH);
        register(Items.GLOW_LICHEN,          LightMode.GLOW, LightColor.DEFAULT, POS_BOTH);
        register(Items.REDSTONE_BLOCK,       LightMode.GLOW, LightColor.RED,     POS_BOTH);
        register(Items.LAVA_BUCKET,          LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.FIRE_CHARGE,          LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.TOTEM_OF_UNDYING,     LightMode.GLOW, LightColor.GREEN,   POS_BOTH);
        register(Items.ENCHANTED_BOOK,       LightMode.GLOW, LightColor.PURPLE,  POS_BOTH);
        register(Items.ENCHANTED_GOLDEN_APPLE, LightMode.GLOW, LightColor.PURPLE, POS_BOTH);
        register(Items.ENCHANTING_TABLE,     LightMode.GLOW, LightColor.PURPLE,  POS_BOTH);
        register(Items.NETHER_STAR,          LightMode.GLOW, LightColor.MAGENTA, POS_BOTH);
        register(Items.ENDER_PEARL,          LightMode.GLOW, LightColor.CYAN,    POS_BOTH);
        register(Items.ENDER_EYE,            LightMode.GLOW, LightColor.GREEN,   POS_BOTH);
        register(Items.DRAGON_EGG,           LightMode.GLOW, LightColor.MAGENTA, POS_BOTH);
        register(Items.ENDER_CHEST,          LightMode.GLOW, LightColor.MAGENTA, POS_BOTH);
        register(Items.EXPERIENCE_BOTTLE,    LightMode.GLOW, LightColor.YELLOW,  POS_BOTH);
        register(Items.END_ROD,              LightMode.GLOW, LightColor.WHITE,   POS_BOTH);
        register(Items.BLAZE_ROD,            LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);
        register(Items.BLAZE_POWDER,         LightMode.GLOW, LightColor.ORANGE,  POS_BOTH);

        register(ModItems.FLASHLIGHT.get(), LightMode.BEAM, LightColor.DEFAULT,   POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(0),  LightMode.BEAM, LightColor.DEFAULT,   POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(1),  LightMode.BEAM, LightColor.WHITE,     POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(2),  LightMode.BEAM, LightColor.LIGHT_GRAY,POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(3),  LightMode.BEAM, LightColor.GRAY,      POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(4),  LightMode.BEAM, LightColor.BLACK,     POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(5),  LightMode.BEAM, LightColor.BROWN,     POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(6),  LightMode.BEAM, LightColor.RED,       POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(7),  LightMode.BEAM, LightColor.ORANGE,    POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(8),  LightMode.BEAM, LightColor.YELLOW,    POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(9),  LightMode.BEAM, LightColor.LIME,      POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(10), LightMode.BEAM, LightColor.GREEN,     POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(11), LightMode.BEAM, LightColor.CYAN,      POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(12), LightMode.BEAM, LightColor.LIGHT_BLUE,POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(13), LightMode.BEAM, LightColor.BLUE,      POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(14), LightMode.BEAM, LightColor.PURPLE,    POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(15), LightMode.BEAM, LightColor.MAGENTA,   POS_BOTH, true);
        register(ModItems.FLASHLIGHT.get(), tag(16), LightMode.BEAM, LightColor.PINK,      POS_BOTH, true);
    }

    public static void register(ItemLike item, LightMode mode, LightColor color, List<LightPosition> positions) {
        register(item, null, mode, color, positions, false);
    }

    public static void register(ItemLike item, CompoundTag filter, LightMode mode, LightColor color, List<LightPosition> positions) {
        register(item, filter, mode, color, positions, false);
    }

    public static void register(ItemLike item, LightMode mode, LightColor color, List<LightPosition> positions, boolean toggleable) {
        register(item, null, mode, color, positions, toggleable);
    }

    public static void register(ItemLike item, CompoundTag filter, LightMode mode, LightColor color, List<LightPosition> positions, boolean toggleable) {
        Item key = item.asItem();
        List<LightItem> list = REGISTRY.computeIfAbsent(key, k -> new ArrayList<>());

        for (LightItem li : list) {
            if (Objects.equals(li.getCompound(), filter)
                    && li.getMode() == mode
                    && li.getColor() == color
                    && Objects.equals(li.getPositions(), positions)
                    && li.isToggleable() == toggleable) {
                return;
            }
        }

        list.add(new LightItem(filter, mode, color, positions, toggleable));
    }

    public static LightItem findFor(ItemStack stack) {
        if (stack.isEmpty()) return null;
        List<LightItem> list = REGISTRY.get(stack.getItem());
        if (list == null || list.isEmpty()) return null;

        LightItem generic = null;
        for (LightItem li : list) {
            CompoundTag pattern = li.getCompound();
            if (pattern == null) {
                generic = li;
            } else if (CompoundTools.itemHasTag(stack, pattern)) {
                return li;
            }
        }
        return generic;
    }

    public static Map<Item, List<LightItem>> getLightItems() {
        return Collections.unmodifiableMap(REGISTRY);
    }

    private static CompoundTag tag(int value) {
        CompoundTag t = new CompoundTag();
        t.putInt("color", value);
        return t;
    }
}