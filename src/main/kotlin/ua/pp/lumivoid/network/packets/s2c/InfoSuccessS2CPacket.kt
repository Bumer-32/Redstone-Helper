package ua.pp.lumivoid.network.packets.s2c

import net.minecraft.util.Identifier

@JvmRecord
data class InfoSuccessS2CPacket(val aMinecraftClass: Identifier)