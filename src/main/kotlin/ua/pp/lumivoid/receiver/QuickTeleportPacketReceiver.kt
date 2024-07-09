package ua.pp.lumivoid.receiver

import net.minecraft.entity.Entity
import net.minecraft.network.packet.s2c.play.PositionFlag
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.QuickTeleportPacket
import java.util.*

object QuickTeleportPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering QuickTeleportPacketReceiver")

        Constants.NET_CHANNEL.registerServerbound(QuickTeleportPacket::class.java) { message, access ->
            val player = access.player
            val world = access.player.serverWorld
            val distance = message.distance
            val includeFluids = message.includeFluids

            logger.debug("quicktp: Teleporting player")

            val hit = player.cameraEntity.raycast(distance.toDouble(), 1.0F, includeFluids)

            val target = player as Entity
            target.teleport(world, hit.pos.x, hit.pos.y, hit.pos.z, EnumSet.noneOf(PositionFlag::class.java), player.getHeadYaw(), player.pitch)
        }
    }
}