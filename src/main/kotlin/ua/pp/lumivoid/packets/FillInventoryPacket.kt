package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class FillInventoryPacket(val blockPos: BlockPos, val item: Identifier, val count: Int, val aMinecraftClass: Identifier)
