package ua.pp.lumivoid.registration

import ua.pp.lumivoid.commands.InstaLamp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object CommandsRegistration {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            InstaLamp.register(dispatcher)
        }
    }
}