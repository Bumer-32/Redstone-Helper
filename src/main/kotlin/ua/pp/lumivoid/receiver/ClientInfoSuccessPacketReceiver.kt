package ua.pp.lumivoid.receiver

import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.packets.InfoSuccessPacket

object ClientInfoSuccessPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClientInfoSuccessPacketReceiver")

        Constants.NET_CHANNEL.registerClientbound(InfoSuccessPacket::class.java) {message, access ->
            HudToast.addToastToQueue(Text.translatable("info.redstone-helper.success"))
        }
    }
}