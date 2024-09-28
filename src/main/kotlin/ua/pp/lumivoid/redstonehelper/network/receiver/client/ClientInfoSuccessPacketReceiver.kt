package ua.pp.lumivoid.redstonehelper.network.receiver.client

import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.network.packets.s2c.InfoSuccessS2CPacket

object ClientInfoSuccessPacketReceiver {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClientInfoSuccessPacketReceiver")

        Constants.NET_CHANNEL.registerClientbound(InfoSuccessS2CPacket::class.java) { message, access ->
            HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_SUCCESS))
        }
    }
}