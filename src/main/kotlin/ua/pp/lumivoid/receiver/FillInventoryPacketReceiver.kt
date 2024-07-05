package ua.pp.lumivoid.receiver

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.FillInventoryPacket
import ua.pp.lumivoid.packets.InfoSuccessPacket
import ua.pp.lumivoid.util.SendPacket

object FillInventoryPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering FillInventoryPacketReceiver")

        Constants.NET_CHANNEL.registerServerbound(FillInventoryPacket::class.java) { message, access ->
            val blockPos = message.blockPos
            val item = Registries.ITEM.get(message.item)
            var amount = message.count

            val blockInventory: Inventory = access.player.serverWorld.getBlockEntity(blockPos) as Inventory // Omg I can get server world from context
            blockInventory.clear()

            if (item.maxCount == 1) {
                if (amount > blockInventory.size()) {
                    amount = blockInventory.size()
                }
                for (i in 0 until amount) {
                    blockInventory.setStack(i, ItemStack(item))
                }
            } else {
                if (amount > blockInventory.size() * item.maxCount) {
                    amount = blockInventory.size() * item.maxCount
                }

                for (i in 0 until amount / item.maxCount + 1) {
                    if (amount >= item.maxCount) {
                        blockInventory.setStack(i, ItemStack(item, item.maxCount))
                        amount -= item.maxCount
                    } else if (amount > 0) {
                        blockInventory.setStack(i, ItemStack(item, amount))
                    }
                }
            }

            SendPacket.sendToPlayer(access.player, InfoSuccessPacket(Constants.aMinecraftClass))
        }
    }
}