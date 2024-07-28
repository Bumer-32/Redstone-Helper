package ua.pp.lumivoid

import net.minecraft.util.math.BlockPos
import ua.pp.lumivoid.util.features.AutoWire

object ClientOptions {
    var isAutoWireEnabled: Boolean = false
    var autoWireMode: AutoWire = AutoWire.AUTO_REDSTONE
    var autoWireLastBlock: BlockPos = BlockPos(0, 0, 0)
    var isAirPlaceEnabled: Boolean = false
    //var autoWireBlockCounter: Int = 1
}