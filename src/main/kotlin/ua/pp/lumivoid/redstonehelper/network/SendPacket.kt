package ua.pp.lumivoid.network

import net.minecraft.server.network.ServerPlayerEntity
import ua.pp.lumivoid.Constants


object SendPacket {
    private val logger = Constants.LOGGER

    fun sendPacket(sendingClass: Record) {
        logger.debug("Sending packet to server")
        Constants.NET_CHANNEL.clientHandle().send(sendingClass)
    }

    fun sendToPlayer(player: ServerPlayerEntity, sendingClass: Record) {
        logger.debug("Sending packet to player")
        Constants.NET_CHANNEL.serverHandle(player).send(sendingClass)
    }
}

