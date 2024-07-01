package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import ua.pp.lumivoid.commands.*

object CommandsRegistration {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            InstaLampCommand.register(dispatcher)
            RedstoneFillCommand.register(dispatcher, registryAccess)
            RedstoneFillSignalCommand.register(dispatcher, registryAccess)
            ClearInventoryCommand.register(dispatcher)
            QuickTpCommand.register(dispatcher)
        }
    }
}