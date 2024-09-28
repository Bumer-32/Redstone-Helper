package ua.pp.lumivoid.redstonehelper.util

import kotlinx.serialization.Serializable
import ua.pp.lumivoid.redstonehelper.util.features.AutoWire

@Serializable
data class JsonConfigData(
    var modVersion: String? = null,
    var versionCheckSkip: String? = null,
    var autoWireMode: AutoWire? = null,
    val macros: MutableList<Macro> = mutableListOf()
)