package ua.pp.lumivoid

import io.wispforest.owo.network.OwoNetChannel
import net.minecraft.util.Identifier

object Constants {
    const val MOD_ID = "redstone-helper"
    const val MOD_VERSION = "0.1.4"
    val NET_CHANNEL: OwoNetChannel = OwoNetChannel.create(Identifier(MOD_ID, "main"))
}
