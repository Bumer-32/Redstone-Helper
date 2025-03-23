package ua.pp.lumivoid.redstonehelper.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.util.features.AutoWire
import ua.pp.lumivoid.redstonehelper.util.features.Macros
import java.io.File

object JsonConfig {
    private val logger = Constants.LOGGER
    private val json = Json { prettyPrint = true }
    private var cachedConfig: JsonConfigData? = null
    private val configFolder = File(Constants.CONFIG_FOLDER_PATH)
    private val configFile = File(Constants.CONFIG_FILE_PATH)

    init {
        if (!configFolder.exists()) configFolder.mkdirs()

    }

    fun readConfig(): JsonConfigData {
        if (cachedConfig != null) return cachedConfig!!

        if (configFile.exists()) {
            val jsonData = configFile.readText()
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
        if (!configFile.exists()) configFile.createNewFile()
        cachedConfig = data
        configFile.writeText(json.encodeToString(data))
    }
}

@Serializable
data class JsonConfigData(
    var modVersion: String? = null,
    var versionCheckSkip: String? = null,
    var autoWireMode: AutoWire? = null,
    val macros: MutableList<Macro> = mutableListOf()
)