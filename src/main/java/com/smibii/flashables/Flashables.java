package com.smibii.flashables;

import com.mojang.logging.LogUtils;
import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.network.NetworkHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ModConstants.MODID)
public class Flashables {
    public static final Logger LOGGER = LogUtils.getLogger();

    public Flashables() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        NetworkHandler.register();
    }

    // @Mod.EventBusSubscriber(modid = ModConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    // public static class ClientModEvents {
    //     @SubscribeEvent
    //     public static void onClientSetup(FMLClientSetupEvent event) {
    //         event.enqueueWork(Flashables::forceEnableVeilPack);
    //     }
    // }

    // protected static void forceEnableVeilPack() {
    //     Minecraft mc = Minecraft.getInstance();
    //     PackRepository repo = mc.getResourcePackRepository();
    //     String packId = "veil:deferred";
    //     List<String> selected = new ArrayList<>(repo.getSelectedIds());
    //     if (!selected.contains(packId)) {
    //         selected.add(packId);
    //         repo.setSelected(selected);
    //         mc.reloadResourcePacks();
    //     }
    // }
}
