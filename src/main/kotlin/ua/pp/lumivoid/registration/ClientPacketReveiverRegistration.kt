package ua.pp.lumivoid.registration

import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.ClientGetItemPacket

object ClientPacketReveiverRegistration {
    fun register() {
        // GetItemPacket receive
        Constants.NET_CHANNEL.registerClientbound(ClientGetItemPacket::class.java) { message, access ->
            println("Clientbound received message: $message")
        }
    }
}