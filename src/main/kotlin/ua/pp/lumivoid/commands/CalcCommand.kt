package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.CalcScreen
import ua.pp.lumivoid.util.Calculate

object CalcCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("calc")
            .executes {
                logger.debug("opening calc menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(CalcScreen())
                })
                1
            }
            .then(ClientCommandManager.argument("expression", StringArgumentType.greedyString())
                .executes { context: CommandContext<FabricClientCommandSource> ->
                    val expression = StringArgumentType.getString(context, "expression")
                    context.source.sendFeedback(Calculate.calc(expression))
                    1
                }
            )
        )
    }
}


