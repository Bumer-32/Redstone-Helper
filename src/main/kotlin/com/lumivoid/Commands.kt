package com.lumivoid

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object Commands {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
        }
    }
}