package ua.pp.lumivoid

import io.wispforest.owo.network.OwoNetChannel
import net.minecraft.SharedConstants
import net.minecraft.util.Identifier

object Constants {
    const val MOD_ID = "redstone-helper"
    const val MOD_VERSION = "0.1.4"
    const val MOD_MODRINTH_ID = "cwYR2Bh1"
    const val MINECRAFT_VERSION = SharedConstants.VERSION_NAME
    val NET_CHANNEL: OwoNetChannel = OwoNetChannel.create(Identifier(MOD_ID, "main"))
}
