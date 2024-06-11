package ua.pp.lumivoid.util

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import ua.pp.lumivoid.ClientOptions
import kotlin.math.abs

enum class AutoWire {
    AUTO_REDSTONE { //temporary not awail because wire cannot place and drops (idk why)
        override fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String {
            setBlock(blockPos, "minecraft:redstone_wire") //wtf? why wire? IT'S DUST!
            return "AUTO_REDSTONE"
        }
    },
    AUTO_LINE {
        override fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String {
            val oldBlockPos = ClientOptions.autoWireLastBlock
            val blockPosDiff = oldBlockPos.subtract(blockPos)
            val blockPosDiffSum = abs(blockPosDiff.x) + abs(blockPosDiff.z)

            //var counter = ClientOptions.autoWireBlockCounter

            val multiplier: Int = if (blockPosDiff.y == 0) {
                1
            } else {
                2
            }

            if (blockPosDiffSum > multiplier) {
//                counter = 1
                setBlock(blockPos, "minecraft:redstone_wire")
            } else {
                try {
                    if (world.getBlockState(oldBlockPos.up()).get(Properties.POWER) == 1) {
                        setBlock(blockPos, "minecraft:repeater", Direction.fromVector(blockPosDiff.x, 0, blockPosDiff.z)!!)
                        setBlock(blockPos.subtract(blockPosDiff).subtract(blockPosDiff).down(), ClientOptions.autoWireBlock)
                        setBlock(blockPos.subtract(blockPosDiff).subtract(blockPosDiff), "minecraft:redstone_wire")
                        setBlock(blockPos.subtract(blockPosDiff), ClientOptions.autoWireBlock)
                    } else {
                        setBlock(blockPos, "minecraft:redstone_wire")
                    }
                } catch (e: IllegalArgumentException) {
                    setBlock(blockPos, "minecraft:redstone_wire")
                }
//                if (counter < 15) { //variant with counter if theres no signal, maybe, for future releases
//                    counter++
//                    setBlock(blockPos, "minecraft:redstone_wire")
//                } else {
//                    counter = 1
//                    setBlock(blockPos, "minecraft:repeater", Direction.fromRotation(player.getHeadYaw().toDouble()))
//                }
            }

            //ClientOptions.autoWireBlockCounter = counter
            ClientOptions.autoWireLastBlock = blockPos
            return "AUTO_LINE"
        }
    },
    AUTO_REPEATER {
        override fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String {
            setBlock(blockPos, "minecraft:repeater", Direction.fromRotation(player.getHeadYaw().toDouble()))
            return "AUTO_REPEATER"
        }
    },
    AUTO_COMPARATOR {
        override fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String {
            setBlock(blockPos, "minecraft:comparator", Direction.fromRotation(player.getHeadYaw().toDouble()))
            return "AUTO_COMPARATOR"
        }
    },
    CHEAP_AUTO_COMPARATOR {
        override fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String {
            val direction = Direction.fromRotation(player.getHeadYaw().toDouble() - 180)
            setBlock(blockPos, "minecraft:comparator", Direction.fromRotation(player.getHeadYaw().toDouble()))
            setBlock(blockPos.offset(direction), ClientOptions.autoWireBlock)
            setBlock(blockPos.offset(direction).offset(direction).down(), ClientOptions.autoWireBlock)
            setBlock(blockPos.offset(direction).offset(direction), "minecraft:redstone_wire")
            setBlock(blockPos.offset(direction).offset(direction).offset(direction).down(), ClientOptions.autoWireBlock)
            setBlock(blockPos.offset(direction).offset(direction).offset(direction), ClientOptions.autoWireBlock)
            // TODO : make redstone dust connected to all sides
            return "COMPACT_AUTO_COMPARATOR"
        }
    };
    
    fun setBlock(blockPos: BlockPos, block: String, direction: Direction = Direction.UP) { //idk why but we can't receive null, server will crash, so I can only use direction up as null
        val blockToSet = Identifier(block)
        SendPackage.SET_BLOCK.sendPacket(blockPos.up(), blockToSet, direction)
    }

    abstract fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String
}