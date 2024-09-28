package ua.pp.lumivoid.redstonehelper.network.receiver.client

import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.network.packets.s2c.InfoBlockNotFoundS2CPacket

object ClientInfoBlockNotFoundPacket {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClientInfoBlockNotFoundPacket")

        Constants.NET_CHANNEL.registerClientbound(InfoBlockNotFoundS2CPacket::class.java) { message, access ->
            HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_BLOCKNOTFOUND))
        }
    }
}