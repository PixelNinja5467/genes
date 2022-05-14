package com.pixelninja.breedsandbeasts;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class GeneArgumentType implements ArgumentType<Gene> {

    protected GeneArgumentType() {
    }

    public static GeneArgumentType gene() {
        return new GeneArgumentType();
    }

    public static Gene getGene(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException{
        return context.getArgument(name, Gene.class);
    }

    @Override
    public Gene parse(StringReader reader) throws CommandSyntaxException {
        return Gene.valueOf(reader.readString().toUpperCase());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (Gene gene : Gene.values()) {
            builder.suggest(gene.getName());
        }
        return builder.buildFuture();
    }
}
