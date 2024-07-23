package ua.pp.lumivoid.network.receiver.server

import net.minecraft.block.enums.WireConnection
import net.minecraft.registry.Registries
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.network.packets.c2s.SetBlockC2SPacket
import ua.pp.lumivoid.util.TickHandler

object SetBlockPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering SetBlockPacketReceiver")

        Constants.NET_CHANNEL.registerServerbound(SetBlockC2SPacket::class.java) { message, access ->
            TickHandler.scheduleAction(1) { // delay, need to place dust because it thinks there's no block under it
                var blockState = Registries.BLOCK.get(message.block).defaultState

                if (message.direction != Direction.UP) {
                    blockState = blockState.with(Properties.HORIZONTAL_FACING, message.direction)
                }

                if (message.block == Identifier.of("minecraft:redstone_wire")) {
                    blockState = blockState.with(Properties.NORTH_WIRE_CONNECTION, WireConnection.SIDE)
                    blockState = blockState.with(Properties.SOUTH_WIRE_CONNECTION, WireConnection.SIDE)
                    blockState = blockState.with(Properties.EAST_WIRE_CONNECTION, WireConnection.SIDE)
                    blockState = blockState.with(Properties.WEST_WIRE_CONNECTION, WireConnection.SIDE)
                }

                access.player.serverWorld.setBlockState(message.blockPos, blockState)
            }

        }
    }
}