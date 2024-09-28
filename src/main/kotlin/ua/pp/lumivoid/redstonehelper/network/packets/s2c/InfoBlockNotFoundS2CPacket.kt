package ua.pp.lumivoid.network.packets.s2c

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class InfoBlockNotFoundS2CPacket(val blockPos: BlockPos, val aMinecraftClass: Identifier)
