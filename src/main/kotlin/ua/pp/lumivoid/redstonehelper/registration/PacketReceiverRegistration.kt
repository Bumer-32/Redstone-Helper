package ua.pp.lumivoid.registration

import ua.pp.lumivoid.network.receiver.server.*

object PacketReceiverRegistration {
    fun register() {
        SetBlockPacketReceiver.register()
        QuickTeleportPacketReceiver.register()
        ClearInventoryPacketReceiver.register()
        FillInventoryPacketReceiver.register()
        SetPlayerSlotInventoryReceiver.register()
    }
}