package ua.pp.lumivoid.registration

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
                    access.player.serverWorld.setBlockState(message.blockPos, blockState)
                }
            }
        }
    }
}