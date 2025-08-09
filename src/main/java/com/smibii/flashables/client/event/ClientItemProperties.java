package com.smibii.flashables.client.event;

import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.items.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ModConstants.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientItemProperties {
    private ClientItemProperties() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    ModItems.FLASHLIGHT.get(),
                    ModConstants.location("color"),
                    (ItemStack stack, ClientLevel level,
                     LivingEntity entity, int seed) -> {
                        if (stack.hasTag() && stack.getTag().contains("color")) {
                            return stack.getTag().getInt("color");
                        }
                        return 0.0f;
                    }
            );
        });
    }
}
