package ua.pp.lumivoid.receiver

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.InfoBlockNotFoundPacket

object ClientInfoBlockNotFoundPacket {
    private val logger = Constants.LOGGER

    fun register() {
        logger.info("Registering ClientInfoBlockNotFoundPacket")

        Constants.NET_CHANNEL.registerClientbound(InfoBlockNotFoundPacket::class.java) { message, access ->
            MinecraftClient.getInstance().inGameHud.chatHud.addMessage(Text.translatable("info_error.redstone-helper.invalid_block_inventory"))
        }
    }
}