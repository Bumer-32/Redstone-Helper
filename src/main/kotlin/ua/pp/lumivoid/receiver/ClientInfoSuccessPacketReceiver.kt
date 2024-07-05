package ua.pp.lumivoid.receiver

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.InfoSuccessPacket

object ClientInfoSuccessPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.info("Registering ClientInfoSuccessPacketReceiver")

        Constants.NET_CHANNEL.registerClientbound(InfoSuccessPacket::class.java) {message, access ->
            MinecraftClient.getInstance().inGameHud.chatHud.addMessage(Text.translatable("info.redstone-helper.success")) //info_error.redstone-helper.invalid_block_inventory
        }
    }
}