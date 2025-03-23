package ua.pp.lumivoid.redstonehelper.network.receiver

import ua.pp.lumivoid.redstonehelper.network.receiver.client.ClientInfoBlockNotFoundPacket
import ua.pp.lumivoid.redstonehelper.network.receiver.client.ClientInfoSuccessPacketReceiver

object ClientPacketReceiverRegistration {
    fun register() {
        ClientInfoSuccessPacketReceiver.register()
        ClientInfoBlockNotFoundPacket.register()
    }
}