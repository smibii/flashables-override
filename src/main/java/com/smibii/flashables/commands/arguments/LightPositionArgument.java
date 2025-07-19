package com.smibii.flashables.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.smibii.flashables.constants.LightPosition;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class LightPositionArgument implements ArgumentType<LightPosition> {
    private static final DynamicCommandExceptionType INVALID_POSITION =
            new DynamicCommandExceptionType(pos -> Component.literal("Invalid light position: " + pos));

    @Override
    public LightPosition parse(StringReader reader) throws CommandSyntaxException {
        String input = reader.readUnquotedString().toUpperCase();
        return Arrays.stream(LightPosition.values())
                .filter(pos -> pos.name().equals(input))
                .findFirst()
                .orElseThrow(() -> INVALID_POSITION.create(input));
    }

    public static LightPositionArgument lightPosition() {
        return new LightPositionArgument();
    }

    public static LightPosition getPosition(CommandContext<?> context, String name) {
        return context.getArgument(name, LightPosition.class);
    }

    public Collection<String> getExamples() {
        return Arrays.asList("MAINHAND", "OFFHAND", "HEAD");
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemaining().toUpperCase();

        Arrays.stream(LightPosition.values())
                .map(Enum::name)
                .filter(name -> name.startsWith(remaining))
                .map(String::toLowerCase)
                .sorted()
                .forEach(builder::suggest);

        return builder.buildFuture();
    }
}