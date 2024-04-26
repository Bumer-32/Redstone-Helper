package com.lumivoid

import com.lumivoid.util.AutoWire
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.util.Identifier

object Options {
    var isAutoWireEnabled: Boolean = false
    var AutoWireMode: AutoWire = AutoWire.AUTO_REDSTONE
    var AutoWireBlock: Identifier = Identifier("minecraft:smooth_stone")
}