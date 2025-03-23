package ua.pp.lumivoid.redstonehelper.network.packets.c2s

import net.minecraft.util.Identifier

@JvmRecord
data class QuickTeleportC2SPacket(val distance: Int, val includeFluids: Boolean, val aMinecraftClass: Identifier)
