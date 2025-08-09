package com.smibii.flashables.items;

import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.util.EnergyNbt;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModConstants.MODID);

    public static final RegistryObject<CreativeModeTab> FLASHABLES_TAB = TABS.register("flashables", () ->
            CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.FLASHLIGHT.get()))
                    .title(Component.translatable("itemGroup." + ModConstants.MODID + ".flashables"))
                    .withTabsBefore(new ResourceLocation("minecraft", "ingredients"))
                    .displayItems((params, output) -> {
                        output.accept(new ItemStack(ModItems.BATTERY.get()));
                        for (int color = 0; color <= 16; color++) {
                            output.accept(flashlight(color));
                        }
                    })
                    .build()
    );

    private static ItemStack flashlight(int colorIndex) {
        ItemStack stack = new ItemStack(ModItems.FLASHLIGHT.get());
        stack.getOrCreateTag().putInt("color", colorIndex);
        return stack;
    }

    private CreativeTab() {}
}