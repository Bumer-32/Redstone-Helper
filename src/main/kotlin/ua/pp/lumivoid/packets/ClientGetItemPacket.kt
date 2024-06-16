package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class ClientGetItemPacket(val blockPos: BlockPos, val slot: Int, val aMinecraftClass: Identifier)