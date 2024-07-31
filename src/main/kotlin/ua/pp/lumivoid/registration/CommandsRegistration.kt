package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import ua.pp.lumivoid.commands.ColorcodeCommand
import ua.pp.lumivoid.commands.InstaLampCommand
import ua.pp.lumivoid.commands.RedstoneGiveCommand
import ua.pp.lumivoid.commands.RedstoneGiveSignalCommandCommand

object CommandsRegistration {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            InstaLampCommand.register(dispatcher)
            RedstoneGiveCommand.register(dispatcher, registryAccess)
            RedstoneGiveSignalCommandCommand.register(dispatcher, registryAccess)
            ColorcodeCommand.register(dispatcher)
        }
    }
}