package ua.pp.lumivoid

import ua.pp.lumivoid.packets.SetBlockPacket
import org.slf4j.LoggerFactory

object PacketReceiver {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)
    fun register() {
        // SET_BLOCK_PACKET receive
        Constants.NET_CHANNEL.registerServerbound(SetBlockPacket::class.java) { message, access ->
            logger.info(message.toString())
        }

        //DEPRECATED
//        ServerPlayNetworking.registerGlobalReceiver(Constants.SET_BLOCK_PACKET) {
//                server: MinecraftServer,
//                player: ServerPlayerEntity,
//                handler: ServerPlayNetworkHandler,
//                buf: PacketByteBuf,
//                sender: PacketSender ->
//
//            val pos = buf.readBlockPos()
//            val blockToSet = Registries.BLOCK.get(buf.readIdentifier())
//
//            server.execute {
//                player.serverWorld.setBlockState(pos, blockToSet.defaultState)
//            }
//        }
    }
}