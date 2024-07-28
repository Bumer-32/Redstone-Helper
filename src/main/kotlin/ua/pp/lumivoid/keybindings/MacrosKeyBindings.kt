package ua.pp.lumivoid.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.Macro
import ua.pp.lumivoid.util.features.Macros

object MacrosKeyBindings {
    private val logger = Constants.LOGGER

    private val macrosKeys = mutableMapOf<String, KeyBinding>()

    fun updateMacros() {
        macrosKeys.clear()
        Macros.listMacros().forEach { macro: Macro ->
            if (macro.enabled) {
                macrosKeys[macro.name] = KeyBinding("", InputUtil.Type.KEYSYM, macro.key, "")
            }
        }
    }

    fun register() {
        updateMacros()
        ClientTickEvents.END_CLIENT_TICK.register {
            macrosKeys.forEach { name, key ->
                while (key.wasPressed()) {
                    logger.info("Executing macro $name")
                    Macros.executeMacro(name)
                }
            }
        }
    }
}