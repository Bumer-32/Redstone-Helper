package ua.pp.lumivoid.util

import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.ClientGetItemPacket
import ua.pp.lumivoid.packets.ClientPutItemPacket
import ua.pp.lumivoid.packets.ClientSetBlockPacket
import ua.pp.lumivoid.packets.ServerGetItemPacket


object SendPacket {
    fun clientSetBlockPacket(pos: BlockPos, block: Identifier, direction: Direction) {
        Constants.NET_CHANNEL.clientHandle().send(ClientSetBlockPacket(pos, block, direction, Identifier.of(Constants.MOD_ID, "main")))
    }

    fun clientPutItemPacket(pos: BlockPos, slot: Int, items: ItemStack) {
        Constants.NET_CHANNEL.clientHandle().send(ClientPutItemPacket(pos, slot, items, Identifier.of(Constants.MOD_ID, "main")))
    }

    fun clientGetItemPacket(pos: BlockPos, slot: Int) {
        Constants.NET_CHANNEL.clientHandle().send(ClientGetItemPacket(pos, slot, Identifier.of(Constants.MOD_ID, "main")))
    }

    fun serverGetItemPacket(items: ItemStack, block: BlockEntity) {
        Constants.NET_CHANNEL.serverHandle(block).send(ServerGetItemPacket(items, Identifier.of(Constants.MOD_ID, "main")))
    }
}

