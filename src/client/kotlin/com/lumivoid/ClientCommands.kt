package com.lumivoid

import com.lumivoid.commands.AutoWireCommand
import com.lumivoid.commands.CalcCommand
import com.lumivoid.commands.RedstoneHelperCommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

object ClientCommands {
    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource?>, registryAccess ->
            CalcCommand.register(dispatcher)
            AutoWireCommand.register(dispatcher)
            RedstoneHelperCommand.register(dispatcher)
        }
    }
}