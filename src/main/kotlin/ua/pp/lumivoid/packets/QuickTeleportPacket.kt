package ua.pp.lumivoid.packets

import net.minecraft.util.Identifier

@JvmRecord
data class QuickTeleportPacket(val distance: Int, val includeFluids: Boolean, val aMinecraftClass: Identifier)
