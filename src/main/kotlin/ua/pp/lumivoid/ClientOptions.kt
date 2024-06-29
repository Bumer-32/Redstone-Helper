package ua.pp.lumivoid

import net.minecraft.util.math.BlockPos
import ua.pp.lumivoid.util.AutoWire

object ClientOptions {
    var isAutoWireEnabled: Boolean = false
    var autoWireMode: AutoWire = AutoWire.AUTO_REDSTONE
    var autoWireLastBlock: BlockPos = BlockPos(0, 0, 0)
    //var autoWireBlockCounter: Int = 1
}