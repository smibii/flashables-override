package com.smibii.flashables.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.smibii.flashables.commands.arguments.LightColorArgument;
import com.smibii.flashables.commands.arguments.LightModeArgument;
import com.smibii.flashables.commands.arguments.LightPositionArgument;
import com.smibii.flashables.common.logic.ServerLightLogic;
import com.smibii.flashables.constants.LightColor;
import com.smibii.flashables.constants.LightMode;
import com.smibii.flashables.constants.LightPosition;
import com.smibii.flashables.constants.ModConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                                Commands.argument("lightPos", LightPositionArgument.lightPosition())
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
                            LightPosition lightPos = LightPositionArgument.getPosition(ctx, "lightPos");
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
                .then(Commands.argument("mode", LightModeArgument.lightMode())
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            LightPosition lightPos = LightPositionArgument.getPosition(ctx, "lightPos");
                            LightMode mode = LightModeArgument.getMode(ctx, "mode");
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
                .then(Commands.argument("color", LightColorArgument.color())
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            LightPosition lightPos = LightPositionArgument.getPosition(ctx, "lightPos");
                            LightColor color = LightColorArgument.getColor(ctx, "color");
                            ServerLightLogic.setColor(
                                    source.getPlayer(),
                                    lightPos,
                                    color
                            );
                            return 1;
                        })
                );
    }
}
