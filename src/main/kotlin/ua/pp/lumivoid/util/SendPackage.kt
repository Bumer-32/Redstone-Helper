package ua.pp.lumivoid.util

import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.SetBlockPacket
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos


enum class SendPackage {
    SET_BLOCK {
        override fun sendPacket(pos: BlockPos, block: String) {
            Constants.NET_CHANNEL.clientHandle().send(SetBlockPacket(pos, block, Identifier("is", "podge")))
        }
    };

    abstract fun sendPacket(pos: BlockPos, block: String)
}

