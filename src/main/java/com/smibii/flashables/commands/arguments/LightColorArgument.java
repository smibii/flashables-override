package com.smibii.flashables.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.smibii.flashables.constants.LightColor;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class LightColorArgument implements ArgumentType<LightColor> {
    private static final DynamicCommandExceptionType INVALID_COLOR =
            new DynamicCommandExceptionType(color -> Component.literal("Invalid color: " + color));

    @Override
    public LightColor parse(StringReader reader) throws CommandSyntaxException {
        String input = reader.readUnquotedString().toUpperCase();
        return Arrays.stream(LightColor.values())
                .filter(c -> c.name().equals(input))
                .findFirst()
                .orElseThrow(() -> INVALID_COLOR.create(input));
    }

    public static LightColorArgument color() {
        return new LightColorArgument();
    }

    public static LightColor getColor(com.mojang.brigadier.context.CommandContext<?> context, String name) {
        return context.getArgument(name, LightColor.class);
    }

    public Collection<String> getExamples() {
        return Arrays.asList("DEFAULT", "WHITE", "RED", "ORANGE", "YELLOW", "LIME");
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemaining().toUpperCase();

        var suggestions = Arrays.stream(LightColor.values())
                .map(Enum::name)
                .filter(name -> name.startsWith(remaining))
                .map(String::toLowerCase)
                .sorted((a, b) -> {
                    if (a.equals("default")) return -1;
                    if (b.equals("default")) return 1;
                    return a.compareTo(b);
                });

        suggestions.forEach(builder::suggest);

        return builder.buildFuture();
    }
}