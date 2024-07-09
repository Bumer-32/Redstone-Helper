package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class InfoBlockNotFoundPacket(val blockPos: BlockPos, val aMinecraftClass: Identifier)
