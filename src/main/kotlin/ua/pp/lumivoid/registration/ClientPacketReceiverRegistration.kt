package ua.pp.lumivoid.registration

import ua.pp.lumivoid.receiver.ClientInfoBlockNotFoundPacket
import ua.pp.lumivoid.receiver.ClientInfoSuccessPacketReceiver

object ClientPacketReceiverRegistration {
    fun register() {
        ClientInfoSuccessPacketReceiver.register()
        ClientInfoBlockNotFoundPacket.register()
    }
}