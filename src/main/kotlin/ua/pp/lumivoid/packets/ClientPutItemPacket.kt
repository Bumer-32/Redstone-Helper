package ua.pp.lumivoid.packets

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class ClientPutItemPacket(val blockPos: BlockPos, val slot: Int, val items: ItemStack, val aMinecraftClass: Identifier)
