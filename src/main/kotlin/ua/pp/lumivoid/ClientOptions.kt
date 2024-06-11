package ua.pp.lumivoid

import ua.pp.lumivoid.util.AutoWire
import net.minecraft.util.Identifier

object ClientOptions {
    var isAutoWireEnabled: Boolean = false
    var autoWireMode: AutoWire = AutoWire.AUTO_REDSTONE
    var autoWireBlock: Identifier = Identifier("minecraft:smooth_stone")
}