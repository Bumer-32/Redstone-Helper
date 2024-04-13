package com.lumivoid.commands

import com.lumivoid.Constants
import com.lumivoid.util.SendSetBlock
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.util.math.BlockPos
import org.slf4j.LoggerFactory

class AutoWireCommand {
    private val logger = LoggerFactory.getLogger(Constants().MOD_ID)

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("autowire")
            .executes {
                SendSetBlock().sendPacket(BlockPos(0, 100, 0),"stone")
                1
            }
        )
    }
}