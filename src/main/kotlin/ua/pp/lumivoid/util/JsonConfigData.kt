package ua.pp.lumivoid.util

import kotlinx.serialization.Serializable
import ua.pp.lumivoid.util.features.AutoWire

@Serializable
data class JsonConfigData(
    var modVersion: String? = null,
    var versionCheckSkip: String? = null,
    var autoWireMode: AutoWire? = null,
    val macros: MutableList<Macro> = mutableListOf()
)