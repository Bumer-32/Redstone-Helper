package ua.pp.lumivoid.packets

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

@JvmRecord
data class ServerGetItemPacket(val items: ItemStack, val aMinecraftClass: Identifier)