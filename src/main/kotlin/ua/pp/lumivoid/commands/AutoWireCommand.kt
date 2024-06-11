package ua.pp.lumivoid.commands

import ua.pp.lumivoid.Constants
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.world.GameMode
import org.slf4j.LoggerFactory

object AutoWireCommand {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("autowire")
            .requires { MinecraftClient.getInstance().interactionManager!!.currentGameMode == GameMode.CREATIVE } // YEYEYEYEYEYYE! I HAVE GAMEMODE ALREADY.
            .executes { context: CommandContext<FabricClientCommandSource> ->
                logger.debug("opening autowire menu")
                MinecraftClient.getInstance().send(Runnable {
                    MinecraftClient.getInstance().setScreen(ua.pp.lumivoid.gui.AutowireScreen())
                })
                //SendPackage.SET_BLOCK.sendPacket(BlockPos(0, 100, 0), "stone")
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