package ua.pp.lumivoid.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ua.pp.lumivoid.Constants
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
                logger.warn("Error while reading config file, rewriting")
                e.printStackTrace()
            }
        }
        val data = JsonConfigData(modVersion = Constants.MOD_VERSION)
        writeConfig(data)
        return data
    }

    fun writeConfig(data: JsonConfigData) {
        cachedConfig = data
        val file = File(Constants.CONFIG_FOLDER_PATH + "\\config.json")
        file.writeText(json.encodeToString(data))
    }
}