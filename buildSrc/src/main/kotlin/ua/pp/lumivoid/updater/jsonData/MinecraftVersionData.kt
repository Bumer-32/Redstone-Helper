package ua.pp.lumivoid.updater.jsonData

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftVersionData(
    val version: String,
    val stable: Boolean
)
