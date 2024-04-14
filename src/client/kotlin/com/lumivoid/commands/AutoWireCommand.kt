package com.lumivoid.commands

import com.lumivoid.Constants
import com.lumivoid.gui.AutoWireGui
import com.mojang.brigadier.CommandDispatcher
import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.world.GameMode
import org.slf4j.LoggerFactory

object AutoWireCommand {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("autowire")
            // You have creative, ok command accepted
            .requires { MinecraftClient.getInstance().interactionManager!!.currentGameMode == GameMode.CREATIVE } // YEYEYEYEYEYYE! I HAVE GAMEMODE ALREADY.
            .executes {
                logger.debug("opening autowire menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(CottonClientScreen(AutoWireGui()))
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