package ua.pp.lumivoid.network.receiver.client

import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.network.packets.s2c.InfoSuccessS2CPacket

object ClientInfoSuccessPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClientInfoSuccessPacketReceiver")

        Constants.NET_CHANNEL.registerClientbound(InfoSuccessS2CPacket::class.java) { message, access ->
            HudToast.addToastToQueue(Text.translatable("redstone-helper.stuff.info.success"))
        }
    }
}