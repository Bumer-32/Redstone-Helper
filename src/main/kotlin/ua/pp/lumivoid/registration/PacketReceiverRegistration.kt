package ua.pp.lumivoid.registration

import ua.pp.lumivoid.network.receiver.server.ClearInventoryPacketReceiver
import ua.pp.lumivoid.network.receiver.server.FillInventoryPacketReceiver
import ua.pp.lumivoid.network.receiver.server.QuickTeleportPacketReceiver
import ua.pp.lumivoid.network.receiver.server.SetBlockPacketReceiver

object PacketReceiverRegistration {
    fun register() {
        SetBlockPacketReceiver.register()
        QuickTeleportPacketReceiver.register()
        ClearInventoryPacketReceiver.register()
        FillInventoryPacketReceiver.register()
    }
}