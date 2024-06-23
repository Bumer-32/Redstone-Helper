package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import net.minecraft.world.GameMode
import ua.pp.lumivoid.Constants

object AutoWireCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("autowire")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context: CommandContext<FabricClientCommandSource> ->
                if (MinecraftClient.getInstance().interactionManager!!.currentGameMode == GameMode.CREATIVE) {
                    logger.debug("opening autowire menu")
                    MinecraftClient.getInstance().send(Runnable {
                        MinecraftClient.getInstance().setScreen(ua.pp.lumivoid.gui.AutowireScreen())
                    })
                } else {
                    context.source.sendFeedback(Text.translatable("needs.redstone-helper.creative_needs"))
                }
                1
            }
        )
    }
}