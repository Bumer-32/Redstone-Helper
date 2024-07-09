package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class ClearInventoryPacket(val blockPos: BlockPos, val aMinecraftClass: Identifier)