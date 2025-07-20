package com.smibii.flashables.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.smibii.flashables.common.logic.ServerLightLogic;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.constants.ModConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = ModConstants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlashablesCommand {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("flashables")
                        .requires(src -> src.hasPermission(2))
                        .then(
                                Commands.argument("lightPos", StringArgumentType.word())
                                        .suggests((context, builder) -> {
                                            generateSuggestions(builder, LightPosition.class);
                                            return builder.buildFuture();
                                        })
                                        .then(createActiveCommand())
                                        .then(createModeCommand())
                                        .then(createColorCommand())
                        )
        );
    }

    private static ArgumentBuilder<CommandSourceStack, ?> createActiveCommand() {
        return Commands.literal("active")
                .then(Commands.argument("bool", BoolArgumentType.bool())
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            LightPosition lightPos = fetchArgument(ctx, "lightPos", LightPosition.class);
                            if (lightPos == null) return 0;

                            boolean active = BoolArgumentType.getBool(ctx, "bool");
                            ServerLightLogic.setLight(
                                    source.getPlayer(),
                                    lightPos,
                                    active
                            );
                            return 1;
                        })
                );
    }

    private static ArgumentBuilder<CommandSourceStack, ?> createModeCommand() {
        return Commands.literal("mode")
                .then(Commands.argument("mode", StringArgumentType.word())
                        .suggests(((ctx, builder) -> {
                            generateSuggestions(builder, LightMode.class);
                            return builder.buildFuture();
                        }))
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            LightPosition lightPos = fetchArgument(ctx, "lightPos", LightPosition.class);
                            if (lightPos == null) return 0;

                            LightMode mode = fetchArgument(ctx, "mode", LightMode.class);
                            if (mode == null) return 0;

                            ServerLightLogic.setMode(
                                    source.getPlayer(),
                                    lightPos,
                                    mode
                            );
                            return 1;
                        })
                );
    }

    private static ArgumentBuilder<CommandSourceStack, ?> createColorCommand() {
        return Commands.literal("color")
                .then(Commands.argument("color", StringArgumentType.word())
                        .suggests(((ctx, builder) -> {
                            generateSuggestions(builder, LightColor.class);
                            return builder.buildFuture();
                        }))
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            LightPosition lightPos = fetchArgument(ctx, "lightPos", LightPosition.class);
                            if (lightPos == null) return 0;

                            LightColor color = fetchArgument(ctx, "color", LightColor.class);
                            if (color == null) return 0;

                            ServerLightLogic.setColor(
                                    source.getPlayer(),
                                    lightPos,
                                    color
                            );
                            return 1;
                        })
                );
    }

    private static void generateSuggestions(SuggestionsBuilder builder, Class<? extends Enum<?>> enumClass) {
        String remaining = builder.getRemaining().toUpperCase();
        Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .filter(name -> name.startsWith(remaining))
                .map(String::toLowerCase)
                .sorted()
                .forEach(builder::suggest);
    }

    private static <T extends Enum<T>> T fetchArgument(CommandContext<CommandSourceStack> ctx, String name,  Class<T> clazz) {
        String argument = StringArgumentType.getString(ctx, name);
        T enumeration;
        try {
            enumeration = (T) Enum.valueOf(clazz, argument.toUpperCase());
        } catch (IllegalArgumentException e) {
            ctx.getSource().sendFailure(Component.literal("Invalid argument: " + argument));
            return null;
        }
        return enumeration;
    }
}
