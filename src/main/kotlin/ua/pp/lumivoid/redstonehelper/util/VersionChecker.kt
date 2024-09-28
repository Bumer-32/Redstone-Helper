package ua.pp.lumivoid.redstonehelper.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import java.net.URL

object VersionChecker {
    private val logger = Constants.LOGGER


    private var isUpdate: Boolean? = null
    private var newestVersion: String? = null
    private var versionType: String? = null

    private fun checkModrinthVersion(checkForRelease: Boolean, checkForBeta: Boolean, checkForAlpha: Boolean): Pair<Boolean, String> {
        logger.info("Checking version of Redstone-Helper")
        if (isUpdate != null ) {
            logger.info("Returning cached version of Redstone-Helper")
            // return cached result
            return Pair(isUpdate!!, "$newestVersion-$versionType")
        }

        var newestVersion = ""
        var versionType = ""

        @Suppress("DEPRECATION")
        val response = URL(Constants.URLS.MODRINTH_API_URL).readText()
        Json.parseToJsonElement(response).jsonArray.forEach { version ->
            newestVersion = version.jsonObject["version_number"]!!.jsonPrimitive.content
            versionType = version.jsonObject["version_type"]!!.jsonPrimitive.content // alpha, beta, release

            this.newestVersion = newestVersion
            this.versionType = versionType

            when {
                versionType == "alpha" && !checkForAlpha -> {
                    isUpdate = false
                    return Pair(false, "$newestVersion-$versionType")
                }
                versionType == "beta" && !checkForBeta -> {
                    isUpdate = true
                    return Pair(true, "$newestVersion-$versionType")
                }
                versionType == "release" && !checkForRelease -> {
                    isUpdate = false
                    return Pair(false, "$newestVersion-$versionType")
                }
            }

            // else check

            if (version.jsonObject["game_versions"]!!.jsonArray.any { it.jsonPrimitive.content == Constants.MINECRAFT_VERSION }) {
                if (newestVersion == Constants.MOD_VERSION) {
                    isUpdate = true
                    return Pair(true, "$newestVersion-$versionType")
                }
                isUpdate = false
                return Pair(false, "$newestVersion-$versionType")
            }
        }
        isUpdate = false
        return Pair(false, "$newestVersion-$versionType")
    }

    fun  skipVersion() {
        val jsonConfig = JsonConfig.readConfig()
        jsonConfig.versionCheckSkip = "$newestVersion-$versionType"
        JsonConfig.writeConfig(jsonConfig)
    }

    fun clearSkippedVersion() {
        val jsonConfig = JsonConfig.readConfig()
        jsonConfig.versionCheckSkip = null
        JsonConfig.writeConfig(jsonConfig)
    }

    fun checkRedstoneHelperVersionWithToast() {
        val config = Config()
        if (config.enableUpdateCheck) {
            val checkerText = checkRedstoneHelperVersionLocalized(
                config.checkForRelease,
                config.checkForBeta,
                config.checkForAlpha
            )

            if (checkerText == Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_UPTODATE)) {
                if (config.showUpToDateNotification) {
                    HudToast.addToastToQueue(checkerText)
                }
            } else {
                HudToast.addToastToQueue(checkerText)
                HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_TOCHECKVERSION))
            }
        }
    }

    fun checkRedstoneHelperVersionLocalized(checkForRelease: Boolean, checkForBeta: Boolean, checkForAlpha: Boolean): Text {
        val version: String

        try {
            val (isUpToDate, thisVersion) = checkModrinthVersion(checkForRelease, checkForBeta, checkForAlpha)
            version = thisVersion

            if (JsonConfig.readConfig().versionCheckSkip == version) {
                return Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_UPTODATE)
            }

            if (isUpToDate) {
                return Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_UPTODATE)
            }
        } catch (e: Exception) {
            logger.error("Error while checking version of Redstone-Helper", e)
            return Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_UNABLETOCHECKVERSION)
        }

        return Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_NEEDUPDATE, version)
    }

    fun checkRedstoneHelperVersionString(): String {
        val version: String
        try {
            val (isUpToDate, thisVersion) = checkModrinthVersion(
                checkForRelease = true,
                checkForBeta = true,
                checkForAlpha = true
            )
            version = thisVersion

            if (isUpToDate) {
                return "Redstone Helper is up to date!"
            }
        } catch (e: Exception) {
            logger.error("Error while checking version of Redstone-Helper", e)
            return "Unable to check Redstone Helper version! You can check it itself https://modrinth.com/mod/redstone-helper Error: $e"
        }
        return "Old version of Redstone Helper detected! Please update to $version https://modrinth.com/mod/redstone-helper"
    }
}