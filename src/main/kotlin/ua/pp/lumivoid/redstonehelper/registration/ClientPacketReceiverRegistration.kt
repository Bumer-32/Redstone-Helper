package ua.pp.lumivoid.registration

import ua.pp.lumivoid.network.receiver.client.ClientInfoBlockNotFoundPacket
import ua.pp.lumivoid.network.receiver.client.ClientInfoSuccessPacketReceiver

object ClientPacketReceiverRegistration {
    fun register() {
        ClientInfoSuccessPacketReceiver.register()
        ClientInfoBlockNotFoundPacket.register()
    }
}