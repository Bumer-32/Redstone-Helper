package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier

@JvmRecord
data class InfoSuccessPacket(val aMinecraftClass: Identifier)