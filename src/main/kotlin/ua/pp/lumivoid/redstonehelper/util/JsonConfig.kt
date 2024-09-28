package ua.pp.lumivoid.redstonehelper.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.util.features.Macros
import java.io.File

object JsonConfig {
    private val logger = Constants.LOGGER
    private val json = Json { prettyPrint = true }
    private var cachedConfig: JsonConfigData? = null

    init {
        if (!File(Constants.CONFIG_FOLDER_PATH).exists()) File(Constants.CONFIG_FOLDER_PATH).mkdirs()
        val configFile = File(Constants.CONFIG_FOLDER_PATH + "\\config.json")
        if (!configFile.exists()) configFile.createNewFile()
    }

    fun readConfig(): JsonConfigData {
        if (cachedConfig != null) return cachedConfig!!

        val file = File(Constants.CONFIG_FOLDER_PATH + "\\config.json")
        if (file.exists()) {
            val jsonData = file.readText()
            try {
                return json.decodeFromString<JsonConfigData>(jsonData)
            } catch (e: RuntimeException) {
                logger.error("Error while reading config file, rewriting")
                e.stackTrace.forEach { logger.error(it.toString()) }
            }
        }
        val data = JsonConfigData(modVersion = Constants.MOD_VERSION)
        writeConfig(data)

        Macros.addMacro(
            Macro(
                "redstoner",
                GLFW.GLFW_KEY_UNKNOWN,
                false,
                mutableListOf( // standard redstoner macro from redstone tools
                    "/redstone-helper notification add-to-queue false Setting redstoner world!",
                    "/gamerule doTileDrops false",
                    "/gamerule doTraderSpawning false",
                    "/gamerule doWeatherCycle false",
                    "/gamerule doDaylightCycle false",
                    "/gamerule doMobSpawning false",
                    "/gamerule doContainerDrops false",
                    "/time set noon",
                    "/weather clear"
                )
            )
        )

        return data
    }

    fun writeConfig(data: JsonConfigData) {
        cachedConfig = data
        val file = File(Constants.CONFIG_FOLDER_PATH + "\\config.json")
        file.writeText(json.encodeToString(data))
    }
}