package ua.pp.lumivoid.registration

import net.minecraft.block.enums.WireConnection
import net.minecraft.registry.Registries
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.SetBlockPacket
import ua.pp.lumivoid.util.TickHandler

object PacketReceiverRegistration {
    fun register() {
        // SET_BLOCK_PACKET receive
        Constants.NET_CHANNEL.registerServerbound(SetBlockPacket::class.java) { message, access ->
            if (message.aMinecraftClass == Identifier(Constants.MOD_ID, "main")) {
                TickHandler.scheduleAction(1) { // delay, need to place dust because it thinks there's no block under it
                    var blockState = Registries.BLOCK.get(message.block).defaultState

                    if (message.direction != Direction.UP) {
                        blockState = blockState.with(Properties.HORIZONTAL_FACING, message.direction)
                    }

                    if (message.block == Identifier("minecraft:redstone_wire")) {
                        println("something")
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
}