package ua.pp.lumivoid.util

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.SetBlockPacket


enum class SendPackage {
    SET_BLOCK {
        override fun sendPacket(pos: BlockPos, block: Identifier, direction: Direction) {
            Constants.NET_CHANNEL.clientHandle().send(SetBlockPacket(pos, block, direction, Identifier(Constants.MOD_ID, "main")))
        }
    };

    abstract fun sendPacket(pos: BlockPos, block: Identifier, direction: Direction)
}

