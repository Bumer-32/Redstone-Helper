package ua.pp.lumivoid

import ua.pp.lumivoid.commands.AutoWireCommand
import ua.pp.lumivoid.commands.CalcCommand
import ua.pp.lumivoid.commands.RedstoneHelperCommand
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