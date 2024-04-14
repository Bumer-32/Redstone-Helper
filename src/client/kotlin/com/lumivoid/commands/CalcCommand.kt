package com.lumivoid.commands

import com.lumivoid.Constants
import com.lumivoid.util.Calculate
import com.lumivoid.gui.CalcGui
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

object CalcCommand {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("calc")
            .executes {
                logger.debug("opening calc menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(CottonClientScreen(CalcGui()))
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


