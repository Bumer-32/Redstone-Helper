package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.CalcScreen
import ua.pp.lumivoid.util.Calculate

object CalcCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/calc: Registering calc command")

        dispatcher.register(ClientCommandManager.literal("calc")
            .executes {
                logger.debug("/calc: Opening calc menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(CalcScreen())
                })
                1
            }
            .then(ClientCommandManager.argument("expression", StringArgumentType.greedyString())
                .executes { context: CommandContext<FabricClientCommandSource> ->
                    logger.debug("/calc: Calculating expression from chat")
                    val expression = StringArgumentType.getString(context, "expression")
                    val calcResult = Calculate.calc(expression)
                    if (calcResult.isNaN()) {
                        context.source.sendFeedback(Text.translatable("redstone-helper.feature.calculator.invalid_expression"))
                    } else {
                        context.source.sendFeedback(Text.translatable("redstone-helper.feature.calculator.result", calcResult))
                    }
                    1
                }
            )
        )
    }
}


