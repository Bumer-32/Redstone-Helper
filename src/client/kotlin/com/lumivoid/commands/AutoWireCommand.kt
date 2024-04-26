package com.lumivoid.commands

import com.lumivoid.Constants
import com.lumivoid.gui.CalcScreen
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import net.minecraft.world.GameMode
import org.slf4j.LoggerFactory

object AutoWireCommand {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("autowire")
            .requires { MinecraftClient.getInstance().interactionManager!!.currentGameMode == GameMode.CREATIVE } // YEYEYEYEYEYYE! I HAVE GAMEMODE ALREADY.
            .executes { context: CommandContext<FabricClientCommandSource> ->
                logger.debug("opening autowire menu")
                context.source.sendFeedback(Text.literal("Not yet runnable"))
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(CalcScreen())
                })
                1
            }
        )
    }
//    .executes {
//        if (MinecraftClient.getInstance().interactionManager!!.currentGameMode == GameMode.CREATIVE) {
//            SendSetBlock().sendPacket(BlockPos(0, 100, 0),"stone")
//        }
//        1
//    }
}