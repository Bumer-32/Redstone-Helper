package com.lumivoid.packets

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

@JvmRecord
data class SetBlockPacket(val blockPos: BlockPos, val block: String, val aMinecraftClass: Identifier)
