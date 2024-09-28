package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.commands.commandsuggestionproviders.MacrosCommandSuggestionProvider
import ua.pp.lumivoid.redstonehelper.gui.MacroScreen
import ua.pp.lumivoid.redstonehelper.util.features.Macros

object MacroCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/macro: Registering macro command")

        dispatcher.register(ClientCommandManager.literal("macro")
            .executes {
                logger.debug("/macro: Opening macro menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(MacroScreen(null))
                })
                1
            }
            .then(ClientCommandManager.argument("macro", StringArgumentType.greedyString())
                .suggests(MacrosCommandSuggestionProvider)
                .executes { context ->
                    logger.debug("/macro: Executing macro")
                    val macro = Macros.readMacro(StringArgumentType.getString(context, "macro"))
                    if (macro != null) {
                        Macros.executeMacro(macro)
                    } else {
                        context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_MACRONOTFOUND))
                    }
                    1
                }
            )
        )
    }
}