package com.lumivoid

import com.lumivoid.commands.CalcCommand
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

class Commands {
    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource?>, _ ->
            CalcCommand().register(dispatcher)
        }
    }
}