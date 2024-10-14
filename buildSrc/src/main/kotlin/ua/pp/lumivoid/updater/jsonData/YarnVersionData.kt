package ua.pp.lumivoid.updater.jsonData

import kotlinx.serialization.Serializable

@Serializable
data class YarnVersionData(
    val gameVersion: String,
    val separator: String,
    val build: Int,
    val maven: String,
    val version: String,
    val stable: Boolean
)
