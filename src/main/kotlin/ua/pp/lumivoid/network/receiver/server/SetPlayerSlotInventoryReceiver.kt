package ua.pp.lumivoid.network.receiver.server

import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.network.packets.c2s.SetPlayerSlotInventoryC2SPacket

object SetPlayerSlotInventoryReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering SetPlayerSlotInventoryReceiver")

        Constants.NET_CHANNEL.registerServerbound(SetPlayerSlotInventoryC2SPacket::class.java) { message, access ->
            access.player.inventory.setStack(message.slot, message.items)
        }

    }
}