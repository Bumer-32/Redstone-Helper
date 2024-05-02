package ua.pp.lumivoid

import ua.pp.lumivoid.commands.InstaLamp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object Commands {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            InstaLamp.register(dispatcher)
        }
    }
}