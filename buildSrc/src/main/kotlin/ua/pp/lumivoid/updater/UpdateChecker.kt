package ua.pp.lumivoid.updater

import kotlinx.serialization.json.Json
import java.net.URL
import nl.adaptivity.xmlutil.serialization.XML
import ua.pp.lumivoid.updater.jsonData.MinecraftVersionData
import ua.pp.lumivoid.updater.jsonData.YarnVersionData
import ua.pp.lumivoid.updater.xmlData.Metadata
import java.io.File

object UpdateChecker {
    val xml = XML ()
    val json = Json

    val newestVersions = mutableMapOf<String, String>()

    fun checkForUpdates(urls: Map<String, String>, minecraftUrl: String, currentMinecraftVersion: String, yarnVersionUrl: String, file: File): Boolean {
        if (!checkMinecraftVersion(minecraftUrl, currentMinecraftVersion)) {
            println("No new minecraft versions found")
            return false
        }

        // Then, found the newest version of yarn
        checkYarnVersion(yarnVersionUrl)

        urls.forEach { key, url ->
            val newestVersion = checkForUpdate(url)

            println("New version found for $key: $newestVersion")
            newestVersions[key] = newestVersion
        }

        println()
        println()
        println()

        println("Dependencies versions list:")
        newestVersions.forEach {key, value ->
            println("$key: $value")
        }

        writeProperties(file)

        return true
    }

    private fun checkMinecraftVersion(url: String, currentMinecraftVersion: String): Boolean {
        println("Checking for minecraft version")
        @Suppress("DEPRECATION") val response = URL(url).readText()

        val versions = json.decodeFromString<List<MinecraftVersionData>>(response)

        versions.forEach { version ->
            if (version.stable) {
                if (version.version != currentMinecraftVersion) {
                    println("Latest found version: ${version.version}")
                    newestVersions["minecraft_version"] = version.version
                    return true // new version
                }
                else return false // no new versions
            }
        }

        return false // no new versions
    }

    private fun checkYarnVersion(url: String): Boolean {
        println("Checking for yarn version")
        @Suppress("DEPRECATION") val response = URL(url).readText()

        val versions = json.decodeFromString<List<YarnVersionData>>(response)

        versions.forEach { version ->
            if (version.gameVersion == newestVersions["minecraft_version"]) {
                println("Latest found version: ${version.version}")
                newestVersions["yarn_version"] = version.version
                return true
            }
        }

        return false
    }

    private fun checkForUpdate(url: String): String {
        println("Check for update $url")

        @Suppress("DEPRECATION")
        val response = URL(url).readText()

        val metadata = xml.decodeFromString(Metadata.serializer(), response)

        val lastVersion = metadata.versioning.latest

        println("Latest found version: $lastVersion")

        return lastVersion.toString()
    }

    private fun writeProperties(file: File) {
        println("Writing props to file")

        val newFileData = mutableListOf<String>()

        file.forEachLine { line ->
            newFileData.add(line.toString())
        }

        newestVersions.forEach { key, value ->
            newFileData.forEach { line ->
                if (line.startsWith(key)) {
                    newFileData[newFileData.indexOf(line)] = "$key=$value"
                }
            }
        }

        file.writeText(newFileData.joinToString("\n"))

        println()

        println("New file:")
        println(file.readText())

        println()

        println("Done")

    }
}