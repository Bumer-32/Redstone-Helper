package ua.pp.lumivoid.redstonehelper.network.receiver

import ua.pp.lumivoid.redstonehelper.network.receiver.server.ClearInventoryPacketReceiver
import ua.pp.lumivoid.redstonehelper.network.receiver.server.FillInventoryPacketReceiver
import ua.pp.lumivoid.redstonehelper.network.receiver.server.QuickTeleportPacketReceiver
import ua.pp.lumivoid.redstonehelper.network.receiver.server.SetBlockPacketReceiver
import ua.pp.lumivoid.redstonehelper.network.receiver.server.SetPlayerSlotInventoryReceiver

object PacketReceiverRegistration {
    fun register() {
        SetBlockPacketReceiver.register()
        QuickTeleportPacketReceiver.register()
        ClearInventoryPacketReceiver.register()
        FillInventoryPacketReceiver.register()
        SetPlayerSlotInventoryReceiver.register()
    }
}