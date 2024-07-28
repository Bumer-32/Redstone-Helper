@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.network.receiver.server

import net.minecraft.inventory.Inventory
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.network.SendPacket
import ua.pp.lumivoid.network.packets.c2s.ClearInventoryC2SPacket
import ua.pp.lumivoid.network.packets.s2c.InfoBlockNotFoundS2CPacket
import ua.pp.lumivoid.network.packets.s2c.InfoSuccessS2CPacket

object ClearInventoryPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClearInventoryPacketReceiver")

        Constants.NET_CHANNEL.registerServerbound(ClearInventoryC2SPacket::class.java) { message, access ->
            val player = access.player
            val blockPos = message.blockPos
            try {
                val blockInventory = player.serverWorld.getBlockEntity(blockPos) as Inventory
                blockInventory.clear()

                player.serverWorld.updateNeighbors(blockPos, player.serverWorld.getBlockState(blockPos).block)

                SendPacket.sendToPlayer(player, InfoSuccessS2CPacket(Constants.aMinecraftClass))
            } catch (e: NullPointerException) {
                logger.error("/clear-inventory: Failed to get block inventory at ${message.blockPos}, think it`s not a block entity with inventory")
                SendPacket.sendToPlayer(player, InfoBlockNotFoundS2CPacket(blockPos, Constants.aMinecraftClass))
            }
        }
    }
}