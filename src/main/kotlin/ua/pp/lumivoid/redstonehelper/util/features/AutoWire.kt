@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.redstonehelper.util.features

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.registry.Registries
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.SetBlockC2SPacket
import ua.pp.lumivoid.redstonehelper.util.JsonConfig
import ua.pp.lumivoid.redstonehelper.util.TickHandler
import kotlin.math.abs

enum class AutoWire {
    AUTO_REDSTONE {
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
                        TickHandler.scheduleAction(1) {
                            val block = Registries.BLOCK.getId(world.getBlockState(blockPos).block).toString()
                            setBlock(blockPos, "minecraft:repeater", Direction.fromVector(blockPosDiff.x, 0, blockPosDiff.z)!!)
                            setBlock(blockPos.subtract(blockPosDiff).subtract(blockPosDiff).down(), block)
                            setBlock(blockPos.subtract(blockPosDiff).subtract(blockPosDiff), "minecraft:redstone_wire")
                            setBlock(blockPos.subtract(blockPosDiff), block)
                        }
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

            TickHandler.scheduleAction(1) { // sleep to find NEW block not old block
                val block = Registries.BLOCK.getId(world.getBlockState(blockPos).block).toString()
                setBlock(blockPos, "minecraft:comparator", Direction.fromRotation(player.getHeadYaw().toDouble()))
                setBlock(blockPos.offset(direction), block)
                setBlock(blockPos.offset(direction).offset(direction).down(), block)
                setBlock(blockPos.offset(direction).offset(direction), "minecraft:redstone_wire")
                setBlock(blockPos.offset(direction).offset(direction).offset(direction).down(), block)
                setBlock(blockPos.offset(direction).offset(direction).offset(direction), block)
            }
            return "COMPACT_AUTO_COMPARATOR"
        }
    };

    private val logger = Constants.LOGGER

    companion object {
        private val values = entries

        fun previous(current: AutoWire): AutoWire {
            val ordinal = current.ordinal
            return if (ordinal == 0) values.last() else values[ordinal - 1]
        }

        fun next(current: AutoWire): AutoWire {
            val ordinal = current.ordinal
            return if (ordinal == values.lastIndex) values.first() else values[ordinal + 1]
        }

        fun setMode(mode: AutoWire) {
            if (Config().rememberLastAutoWireMode) {
                val data = JsonConfig.readConfig()
                data.autoWireMode = mode
                JsonConfig.writeConfig(data)
            }
            ClientOptions.autoWireMode = mode
        }
    }

    fun setBlock(blockPos: BlockPos, block: String, direction: Direction = Direction.UP) { //idk why but we can't receive null, server will crash, so I can only use direction up as null
        logger.debug("Trying to set $block block at $blockPos")

        val blockToSet = Identifier.of(block)
        SendPacket.sendPacket(SetBlockC2SPacket(blockPos.up(), blockToSet, direction, Constants.aMinecraftClass))
    }

    abstract fun place(blockPos: BlockPos, player: PlayerEntity, world: World): String
}