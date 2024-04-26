package com.lumivoid.util

import com.lumivoid.Constants
import com.lumivoid.Options
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.util.math.BlockPos

enum class SendPackage {
    SET_BLOCK {
        fun sendPacket(pos: BlockPos, block: String) {
            val buf = PacketByteBufs.create()
            buf.writeBlockPos(pos)
            buf.writeIdentifier(Options.AutoWireBlock)
            ClientPlayNetworking.send(Constants.SET_BLOCK_PACKET, buf)
        }
    };
}