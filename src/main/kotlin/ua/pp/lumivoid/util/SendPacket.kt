package ua.pp.lumivoid.util

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.SetBlockPacket


object SendPacket {
    private val logger = Constants.LOGGER

    fun setBlockPacket(pos: BlockPos, block: Identifier, direction: Direction) {
        logger.debug("Sending setBlockPacket to server")
        Constants.NET_CHANNEL.clientHandle().send(SetBlockPacket(pos, block, direction, Identifier.of(Constants.MOD_ID, "main")))
    }
}

