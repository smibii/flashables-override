package com.smibii.flashables.items;

import com.smibii.flashables.constants.ModConstants;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModConstants.MODID);

    public static final RegistryObject<Item> BATTERY = ITEMS.register(
            "battery", () -> new BatteryItem(new Item.Properties())
    );

    public static final RegistryObject<Item> FLASHLIGHT = ITEMS.register(
            "flashlight", () -> new FlashlightItem(new Item.Properties())
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
