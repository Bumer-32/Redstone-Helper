package com.lumivoid.util

import com.lumivoid.Constants
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

class SendSetBlock {
    fun sendPacket(pos: BlockPos, block: String) {
        val buf = PacketByteBufs.create()
        buf.writeBlockPos(pos)
        buf.writeIdentifier(Identifier("minecraft", block)) // Here in second arg set the block (for exhample stone)
        ClientPlayNetworking.send(Constants().SET_BLOCK_PACKET, buf)
    }
}