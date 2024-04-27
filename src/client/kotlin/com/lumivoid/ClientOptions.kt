package com.lumivoid

import com.lumivoid.util.AutoWire
import net.minecraft.util.Identifier

object ClientOptions {
    var isAutoWireEnabled: Boolean = false
    var autoWireMode: AutoWire = AutoWire.AUTO_REDSTONE
    var autoWireBlock: Identifier = Identifier("minecraft:smooth_stone")
}