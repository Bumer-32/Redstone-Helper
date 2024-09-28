package ua.pp.lumivoid.network.packets.c2s

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

@JvmRecord
data class SetPlayerSlotInventoryC2SPacket(val slot: Int, val items: ItemStack, val aMinecraftClass: Identifier)