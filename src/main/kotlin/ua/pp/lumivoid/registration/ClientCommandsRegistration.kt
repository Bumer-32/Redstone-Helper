package ua.pp.lumivoid.registration

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import ua.pp.lumivoid.commands.*

object ClientCommandsRegistration {
    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource?>, registryAccess ->
            CalcCommand.register(dispatcher)
            AutoWireCommand.register(dispatcher)
            RedstoneHelperCommand.register(dispatcher)
            CalcRedstoneSignalCommand.register(dispatcher, registryAccess)
            BitsCommand.register(dispatcher)
        }
    }
}