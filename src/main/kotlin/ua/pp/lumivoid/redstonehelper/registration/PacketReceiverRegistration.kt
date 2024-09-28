package ua.pp.lumivoid.redstonehelper.registration

import ua.pp.lumivoid.redstonehelper.network.receiver.server.*

object PacketReceiverRegistration {
    fun register() {
        SetBlockPacketReceiver.register()
        QuickTeleportPacketReceiver.register()
        ClearInventoryPacketReceiver.register()
        FillInventoryPacketReceiver.register()
        SetPlayerSlotInventoryReceiver.register()
    }
}