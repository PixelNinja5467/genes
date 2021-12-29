package com.pixelninja.breedsandbeasts;

import com.mojang.brigadier.CommandDispatcher;
import com.pixelninja.breedsandbeasts.component.GeneComponent;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Collection;

public class GeneCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("gene")
            .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("entities", EntityArgumentType.entities())
                    .then(CommandManager.literal("add")
                        .then(CommandManager.argument("gene", GeneArgumentType.gene())
                            .executes(context -> executeAddGene(context.getSource(), EntityArgumentType.getEntities(context, "entities"), GeneArgumentType.getGene(context, "gene"))))))
        );
    }

    public static int executeAddGene(ServerCommandSource source, Collection<? extends net.minecraft.entity.Entity> entities, Gene gene) {
        for (Entity entity : entities) {
            GeneComponent.KEY.get(entity).addGene(gene);
        }
        source.sendFeedback(new LiteralText("Added the ").append(gene.name()).append(" gene to ").append(String.valueOf(entities.size())).append(" entities."), true);
        return 1;
    }

    public static int executeRemoveGene(ServerCommandSource source, Collection<? extends net.minecraft.entity.Entity> entities, Gene gene) {
        for (Entity entity : entities) {
            GeneComponent.KEY.get(entity).removeGene(gene);
        }
        source.sendFeedback(new LiteralText("Removed the ").append(gene.name()).append(" gene to ").append(String.valueOf(entities.size())).append(" entities."), true);
        return 1;
    }

}
