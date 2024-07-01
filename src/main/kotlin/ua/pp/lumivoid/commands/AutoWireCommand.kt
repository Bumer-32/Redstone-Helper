package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.AutowireScreen

object AutoWireCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/autowire: Registering autowire command")

        val autoWireCommandNode: LiteralCommandNode<FabricClientCommandSource?> = dispatcher.register(ClientCommandManager.literal("autowire")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes {
                logger.debug("/autowire: Opening autowire menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(AutowireScreen())
                })
                1
            }
        )

        dispatcher.register(ClientCommandManager.literal("autodust")
            .requires { source -> source.hasPermissionLevel(2) }
            // We can't use .redirect because https://github.com/Mojang/brigadier/issues/46 wtf mojang! 2018!!!
            .executes { context ->
                dispatcher.execute("autowire", context.source)
            }
        )
    }
}