package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.BitsScreen

object BitsCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/bits: Registering bits command")

        dispatcher.register(ClientCommandManager.literal("bits")
            .executes {
                logger.debug("/bits: Opening bits menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(BitsScreen())
                })
                1
            }
        )
    }
}