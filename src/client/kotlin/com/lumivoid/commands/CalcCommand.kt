package com.lumivoid.commands

import com.lumivoid.gui.CalcGui
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import org.mariuszgromada.math.mxparser.Expression
import org.slf4j.LoggerFactory

class CalcCommand {
    private val logger = LoggerFactory.getLogger("redstone-helper")

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("calc")
            .executes {
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(CottonClientScreen(CalcGui()))
                })
                1
            }
            .then(ClientCommandManager.argument("expression", StringArgumentType.greedyString())
                    .executes { context: CommandContext<FabricClientCommandSource> ->
                        val expression = StringArgumentType.getString(context, "expression")
                        val result = calc(expression)
                        context.source.sendFeedback(Text.literal(result.toString()))
                        1
                    }
            )

        )
    }

     private fun calc(expression: String): Any {
        logger.debug("Calculating expression: {}", expression)
        val result = Expression(prepareExpression(expression))
        val calculationResult = result.calculate()
         if (calculationResult.isNaN()) {
             logger.debug("Calculated expression is NaN")
             return "Invalid expression"
         } else {
             logger.debug("Calculated expression: {}", calculationResult)
             return calculationResult
         }
    }
    private fun prepareExpression(expression: String): String {
        return expression.replace("multiply", "*").replace(" * ", "*")
    }

}


