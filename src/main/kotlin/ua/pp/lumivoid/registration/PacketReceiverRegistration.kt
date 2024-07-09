package ua.pp.lumivoid.registration

import ua.pp.lumivoid.receiver.ClearInventoryPacketReceiver
import ua.pp.lumivoid.receiver.FillInventoryPacketReceiver
import ua.pp.lumivoid.receiver.QuickTeleportPacketReceiver
import ua.pp.lumivoid.receiver.SetBlockPacketReceiver

object PacketReceiverRegistration {
    fun register() {
        SetBlockPacketReceiver.register()
        QuickTeleportPacketReceiver.register()
        ClearInventoryPacketReceiver.register()
        FillInventoryPacketReceiver.register()
    }
}