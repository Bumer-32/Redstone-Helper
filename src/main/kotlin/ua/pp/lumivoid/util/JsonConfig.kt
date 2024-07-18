package ua.pp.lumivoid.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ua.pp.lumivoid.Constants
import java.io.File

object JsonConfig {
    private val json = Json { prettyPrint = true }

    fun readConfig(): Data? {
        val file = File(Constants.CONFIG_FOLDER_PATH + "\\config.json")
        if (file.exists()) {
            val jsonData = file.readText()
            return json.decodeFromString<Data>(jsonData)
        }
        return null
    }

    fun writeConfig(data: Data) {
        val file = File(Constants.CONFIG_FOLDER_PATH + "\\config.json")
        file.writeText(json.encodeToString(data))
    }
}

@Serializable
data class Data(val modVersion: String)