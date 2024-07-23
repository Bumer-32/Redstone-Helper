package ua.pp.lumivoid.network.receiver.client

import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.network.packets.s2c.InfoBlockNotFoundS2CPacket

object ClientInfoBlockNotFoundPacket {
    private val logger = Constants.LOGGER

    fun register() {
        logger.debug("Registering ClientInfoBlockNotFoundPacket")

        Constants.NET_CHANNEL.registerClientbound(InfoBlockNotFoundS2CPacket::class.java) { message, access ->
            HudToast.addToastToQueue(Text.translatable("info_error.redstone-helper.invalid_block_inventory"))
        }
    }
}