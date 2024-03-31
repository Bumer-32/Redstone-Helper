package com.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import org.mariuszgromada.math.mxparser.Expression
import org.slf4j.LoggerFactory

class CalcCommand {
    private val logger = LoggerFactory.getLogger("redstone-helper")
    fun register(dispatcher: CommandDispatcher<ServerCommandSource> ) {
        dispatcher.register(
            CommandManager.literal("calc")
                .then(
                    CommandManager.argument("expression", StringArgumentType.string())
                        .executes { context ->
                            try {
                                val expression = StringArgumentType.getString(context, "expression")
                                val result = calc(expression)
                                context.getSource().sendFeedback({ Text.literal(result.toString()) }, false)
                            } catch (error: Exception) {
                                context.getSource().sendFeedback({ Text.literal(error.toString()) }, false)
                                logger.error(error.toString())
                            }
                            1
                        }
                ))
    }

    fun calc(expression: String): Any? {
        val result: Expression = Expression(expression)
        return result.calculate()
    }
}
