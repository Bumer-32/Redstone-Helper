@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.receiver

import net.minecraft.inventory.Inventory
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.ClearInventoryPacket
import ua.pp.lumivoid.packets.InfoBlockNotFoundPacket
import ua.pp.lumivoid.packets.InfoSuccessPacket
import ua.pp.lumivoid.util.SendPacket

object ClearInventoryPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClearInventoryPacketReceiver")

        Constants.NET_CHANNEL.registerServerbound(ClearInventoryPacket::class.java) {message, access ->
            val player = access.player
            val blockPos = message.blockPos
            try {
                val blockInventory = player.serverWorld.getBlockEntity(blockPos) as Inventory
                blockInventory.clear()

                SendPacket.sendToPlayer(player, InfoSuccessPacket(Constants.aMinecraftClass))
            } catch (e: NullPointerException) {
                logger.error("/clear-inventory: Failed to get block inventory at ${message.blockPos}, think it`s not a block entity with inventory")
                SendPacket.sendToPlayer(player, InfoBlockNotFoundPacket(blockPos, Constants.aMinecraftClass))
            }
        }
    }
}