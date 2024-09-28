package ua.pp.lumivoid.util.features

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.JsonConfig
import ua.pp.lumivoid.util.Macro
import java.io.File

object Macros {
    private val logger = Constants.LOGGER
    private val json = Json { prettyPrint = true }
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

    fun exportMacro(path: String, macros: MutableList<String>) {
        val macrosForExport: MutableList<Macro> = mutableListOf()

        macros.forEach { macro ->
            val macroData = readMacro(macro)
            if (macroData != null) macrosForExport.add(macroData)
            else logger.error("Macro $macro not found")
        }

        val file = File(path)
        file.writeText(json.encodeToString(macrosForExport))
    }

    /**
     * Note, importMacro() isn't adds macro to config!!
     * It just reads macro from file and returns it
     */
    fun importMacro(path: String): MutableList<Macro>? {
        val file = File(path)
        val jsonData = file.readText()

        try {
            return json.decodeFromString<MutableList<Macro>>(jsonData)
        } catch (e: RuntimeException) {
            logger.error("Error while reading macro file")
            e.stackTrace.forEach { logger.error(it.toString()) }
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