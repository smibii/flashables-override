package com.smibii.flashables.crafting;

import com.smibii.flashables.constants.ModConstants;
import com.smibii.flashables.crafting.recipes.FlashlightRecipe;
import com.smibii.flashables.crafting.recipes.RecolorFlashlightRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModConstants.MODID);

    public static final RegistryObject<RecipeSerializer<RecolorFlashlightRecipe>> RECOLOR_FLASHLIGHT =
            SERIALIZERS.register("recolor_flashlight_recipe",
                    () -> new SimpleCraftingRecipeSerializer<>(RecolorFlashlightRecipe::new));

    public static final RegistryObject<RecipeSerializer<FlashlightRecipe>> FLASHLIGHT =
            SERIALIZERS.register("flashlight_recipe",
                    () -> new SimpleCraftingRecipeSerializer<>(FlashlightRecipe::new));

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
