package ua.pp.lumivoid.registration

import net.minecraft.block.enums.WireConnection
import net.minecraft.inventory.Inventory
import net.minecraft.registry.Registries
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.ClientGetItemPacket
import ua.pp.lumivoid.packets.ClientPutItemPacket
import ua.pp.lumivoid.packets.ClientSetBlockPacket
import ua.pp.lumivoid.util.SendPacket
import ua.pp.lumivoid.util.TickHandler

object PacketReceiverRegistration {
    fun register() {
        // SetBlockPacket receive
        Constants.NET_CHANNEL.registerServerbound(ClientSetBlockPacket::class.java) { message, access ->
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

        // PutItemPacket receive
        Constants.NET_CHANNEL.registerServerbound(ClientPutItemPacket::class.java) { message, access ->
             val blockInventory = access.player.serverWorld.getBlockEntity(message.blockPos) as Inventory
             blockInventory.setStack(message.slot, message.items)
        }

        // GetItemPacket receive
        Constants.NET_CHANNEL.registerServerbound(ClientGetItemPacket::class.java) { message, access ->
            val block = access.player.serverWorld.getBlockEntity(message.blockPos)
            val blockInventory = block as Inventory
            SendPacket.serverGetItemPacket(blockInventory.getStack(message.slot), block)
        }
    }
}