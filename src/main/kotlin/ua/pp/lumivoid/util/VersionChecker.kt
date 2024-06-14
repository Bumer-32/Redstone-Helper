package ua.pp.lumivoid.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import java.net.URL

object VersionChecker {
    private val logger = Constants.LOGGER

    private fun checkModrinthVersion(): Triple<Boolean, Boolean, String> {
        logger.info("Checking version of Redstone-Helper")
        var newestVersion = ""
        try {
            val response = URL("https://api.modrinth.com/v2/project/${Constants.MOD_MODRINTH_ID}/version").readText()
            Json.parseToJsonElement(response).jsonArray.forEach { version ->
                newestVersion = version.jsonObject["version_number"]!!.jsonPrimitive.content
                if (version.jsonObject["game_versions"]!!.jsonArray.first().jsonPrimitive.content == Constants.MINECRAFT_VERSION) {
                    if (newestVersion == Constants.MOD_VERSION) {
                        return Triple(true, true, newestVersion)
                    }
                    return Triple(false, true, newestVersion)
                }
            }
            return Triple(false, true, newestVersion)
        } catch (e: Exception) {
            return Triple(false, false, e.toString())
        }
    }

    fun checkRedstoneHelperVersionLocalized(): Text {
        val (isUpToDate, isSuccess, version) = checkModrinthVersion()

        if (!isSuccess) {
            return Text.translatable("info.redstone-helper.unable_to_check_version", version)
        }
        if (isUpToDate) {
            return Text.translatable("info.redstone-helper.up_to_date")
        }
        return Text.translatable("info.redstone-helper.need_update", version)
    }

    fun checkRedstoneHelperVersionString(): String {
        val (isUpToDate, isSuccess, version) = checkModrinthVersion()

        if (!isSuccess) {
            return "Unable to check Redstone Helper version! You can check it itself https://modrinth.com/mod/redstone-helper Error: $version"
        }
        if (isUpToDate) {
            return "Redstone Helper is up to date!"
        }
        return "Old version of Redstone Helper detected! Please update to $version https://modrinth.com/mod/redstone-helper"
    }
}