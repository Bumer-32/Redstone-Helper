package ua.pp.lumivoid.redstonehelper.network.packets.c2s

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class ClearInventoryC2SPacket(val blockPos: BlockPos, val aMinecraftClass: Identifier)