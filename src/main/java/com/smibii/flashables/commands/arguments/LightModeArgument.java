package com.smibii.flashables.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.smibii.flashables.constants.LightMode;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class LightModeArgument implements ArgumentType<LightMode> {
    private static final DynamicCommandExceptionType INVALID_POSITION =
            new DynamicCommandExceptionType(pos -> Component.literal("Invalid light position: " + pos));

    @Override
    public LightMode parse(StringReader reader) throws CommandSyntaxException {
        String input = reader.readUnquotedString().toUpperCase();
        return Arrays.stream(LightMode.values())
                .filter(mode -> mode.name().equals(input))
                .findFirst()
                .orElseThrow(() -> INVALID_POSITION.create(input));
    }

    public static LightModeArgument lightMode() {
        return new LightModeArgument();
    }

    public static LightMode getMode(CommandContext<?> context, String name) {
        return context.getArgument(name, LightMode.class);
    }

    public Collection<String> getExamples() {
        return Arrays.asList("BEAM", "GLOW");
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemaining().toUpperCase();

        Arrays.stream(LightMode.values())
                .map(Enum::name)
                .filter(name -> name.startsWith(remaining))
                .map(String::toLowerCase)
                .sorted()
                .forEach(builder::suggest);

        return builder.buildFuture();
    }
}
