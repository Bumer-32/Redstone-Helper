package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

@JvmRecord
data class ClientSetBlockPacket(val blockPos: BlockPos, val block: Identifier, val direction: Direction, val aMinecraftClass: Identifier)
