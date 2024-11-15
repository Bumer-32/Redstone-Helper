package ua.pp.lumivoid.redstonehelper.util

import ua.pp.lumivoid.redstonehelper.Constants
import java.io.File
import java.net.URL

object DownloadManager {
    private val logger = Constants.LOGGER

    private val files = File(Constants.CONFIG_FOLDER_PATH + "/downloaded")

    init {
        if (!files.exists()) files.mkdirs()
    }

    fun downloadFile(url: String, fileName: String): String? {
        logger.info("Downloading file $fileName from $url")
        val filePath = "${files.absolutePath}/${Constants.MOD_VERSION}_${fileName}"
        val file = File(filePath)
        if (file.exists()) {
            logger.info("$fileName already exists")
            return(filePath)
        }

        // else download
        try {
            file.createNewFile()

            @Suppress("DEPRECATION")
            val urlConnection = URL(url).openConnection()
            urlConnection.getInputStream().use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.transferTo(outputStream)
                }
            }
            logger.info("Successfully downloaded $fileName")
            return filePath
        } catch (e: Exception) {
            logger.error("Failed to download $fileName")
            e.stackTrace.forEach { logger.error(it.toString()) }
        }

        return null
    }

    fun cleanUp() {
        files.listFiles()?.forEach { it.delete() }
    }
}