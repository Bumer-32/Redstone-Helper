package ua.pp.lumivoid.util.features

import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.JsonConfig
import ua.pp.lumivoid.util.Macro

object Macros {
    private val logger = Constants.LOGGER

    private var macrosCache: MutableList<Macro>? = null

    fun addMacro(macro: Macro) {
        val data = JsonConfig.readConfig()
        val macros = data.macros
        macros.add(macro)
        JsonConfig.writeConfig(data)
    }

    fun editMacro(name: String, macroEdit: Macro): Boolean {
        val data = JsonConfig.readConfig()
        val macros = data.macros
        macros.forEach { macro ->
            if (macro.name == name) {
                macro.name = macroEdit.name
                macro.key = macroEdit.key
                macro.commands = macroEdit.commands
            }
        }
        macrosCache = macros
        JsonConfig.writeConfig(data)
        return false
    }

    fun removeMacro(name: String): Boolean {
        val data = JsonConfig.readConfig()
        val macros = data.macros
        macros.forEach { macro ->
            if (macro.name == name) {
                macros.remove(macro)
                macrosCache = macros
                JsonConfig.writeConfig(data)
                return true
            }
        }
        return false
    }

    fun listMacros(): MutableList<Macro> = macrosCache ?: JsonConfig.readConfig().macros

    fun readMacro(name: String): Macro? {
        val macros: MutableList<Macro> = macrosCache ?: JsonConfig.readConfig().macros
        macros.forEach { macro ->
            if (macro.name == name) {
                return macro
            }
        }

        return null
    }

    fun executeMacro(name: String): Boolean {
        val macro = readMacro(name)
        if (macro != null) {
            executeMacro(macro)
            return true
        }
        return false
    }

    fun executeMacro(macro: Macro) {
        macro.commands.forEach { command ->
            logger.debug("/macro: Executing command: $command")
            if (command.startsWith("/")) {
                MinecraftClient.getInstance().player!!.networkHandler.sendCommand(command.replaceFirst("/", ""))
            } else {
                MinecraftClient.getInstance().player!!.networkHandler.sendCommand(command)
            }
        }
    }
}