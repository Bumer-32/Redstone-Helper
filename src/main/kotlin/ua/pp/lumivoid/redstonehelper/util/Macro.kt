package ua.pp.lumivoid.redstonehelper.util

import kotlinx.serialization.Serializable

@Serializable
data class Macro(var name: String, var key: Int, var enabled: Boolean, var commands: MutableList<String>)
