package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import ua.pp.lumivoid.commands.InstaLamp

object CommandsRegistration {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            InstaLamp.register(dispatcher)
            //RedstoneFill.register(dispatcher)
        }
    }
}