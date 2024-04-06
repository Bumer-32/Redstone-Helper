package com.lumivoid

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

class Commands {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
        }
    }
}