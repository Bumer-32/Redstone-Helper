package ua.pp.lumivoid.network.packets.c2s

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class FillInventoryC2SPacket(val blockPos: BlockPos, val item: Identifier, val count: Int, val aMinecraftClass: Identifier)
