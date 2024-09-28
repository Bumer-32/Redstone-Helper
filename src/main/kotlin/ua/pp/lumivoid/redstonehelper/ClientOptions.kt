package ua.pp.lumivoid.redstonehelper

import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import ua.pp.lumivoid.redstonehelper.util.features.AutoWire

object ClientOptions {
    var isAutoWireEnabled: Boolean = false
    var autoWireMode: AutoWire = AutoWire.AUTO_REDSTONE
    var autoWireLastBlock: BlockPos = BlockPos(0, 0, 0)
    var isAirPlaceEnabled: Boolean = false
    var rememberLastSelectedItems: ItemStack? = null
    var illegalFeatureNotified: Boolean = false
    //var autoWireBlockCounter: Int = 1
}