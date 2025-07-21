package com.smibii.flashables;

import com.mojang.logging.LogUtils;
import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.network.NetworkHandler;
import foundry.veil.api.quasar.emitters.shape.Sphere;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(ModConstants.MODID)
public class Flashables {
    public static final Logger LOGGER = LogUtils.getLogger();

    public Flashables() {
        NetworkHandler.register();
    }

    @Mod.EventBusSubscriber(modid = ModConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();
                PackRepository repo = mc.getResourcePackRepository();
                String packId = "veil:deferred";
                List<String> selected = new ArrayList<>(repo.getSelectedIds());
                if (!selected.contains(packId)) {
                    selected.add(packId);
                    repo.setSelected(selected);
                    mc.reloadResourcePacks();
                }
            });
        }
    }
}
