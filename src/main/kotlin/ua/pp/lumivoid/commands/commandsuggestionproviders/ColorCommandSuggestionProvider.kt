package ua.pp.lumivoid.commands.commandsuggestionproviders

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.DyeColor
import java.util.concurrent.CompletableFuture

object ColorCommandSuggestionProvider : SuggestionProvider<ServerCommandSource> {
    override fun getSuggestions(context: CommandContext<ServerCommandSource>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        DyeColor.entries.forEach { dyeColor ->
            builder.suggest(dyeColor.name.lowercase())
        }

        return builder.buildFuture()
    }
}