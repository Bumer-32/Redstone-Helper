package ua.pp.lumivoid.redstonehelper.network.packets.c2s

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

@JvmRecord
data class SetBlockC2SPacket(val blockPos: BlockPos, val block: Identifier, val direction: Direction, val isAuto: Boolean, val aMinecraftClass: Identifier)
