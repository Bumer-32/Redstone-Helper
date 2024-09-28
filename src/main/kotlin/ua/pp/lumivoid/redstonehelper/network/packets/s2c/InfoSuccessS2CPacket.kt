package ua.pp.lumivoid.redstonehelper.network.packets.s2c

import net.minecraft.util.Identifier

@JvmRecord
data class InfoSuccessS2CPacket(val aMinecraftClass: Identifier)