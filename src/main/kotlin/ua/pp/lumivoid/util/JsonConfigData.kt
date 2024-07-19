package ua.pp.lumivoid.util

import kotlinx.serialization.Serializable

@Serializable
data class JsonConfigData(
    var modVersion: String? = null,
    var versionCheckSkip: String? = null
)