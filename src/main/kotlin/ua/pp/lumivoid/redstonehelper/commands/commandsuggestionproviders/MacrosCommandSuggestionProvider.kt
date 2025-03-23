package ua.pp.lumivoid.redstonehelper.commands.commandsuggestionproviders

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import ua.pp.lumivoid.redstonehelper.util.features.Macros
import java.util.concurrent.CompletableFuture

object MacrosCommandSuggestionProvider: SuggestionProvider<FabricClientCommandSource>{
    override fun getSuggestions(context: CommandContext<FabricClientCommandSource>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val macros = Macros.listMacros()

        macros.forEach { macro ->
            builder.suggest(macro.name)
        }

        return builder.buildFuture()
    }
}