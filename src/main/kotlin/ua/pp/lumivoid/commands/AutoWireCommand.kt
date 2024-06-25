package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.Constants

object AutoWireCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/autowire: Registering autowire command")

        dispatcher.register(ClientCommandManager.literal("autowire")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context: CommandContext<FabricClientCommandSource> ->
                logger.debug("/autowire: Opening autowire menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(ua.pp.lumivoid.gui.AutowireScreen())
                })
                1
            }
        )
    }
}