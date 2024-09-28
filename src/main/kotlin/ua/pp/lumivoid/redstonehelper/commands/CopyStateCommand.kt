package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.NbtComponent
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import ua.pp.lumivoid.redstonehelper.Constants

object CopyStateCommand {
    private val logger = Constants.LOGGER
    //TODO

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/copystate: Registering copystate Command")

        dispatcher.register(ClientCommandManager.literal("copystate")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                val player = context.source.player!!
                val hit = context.source.client.crosshairTarget!! as BlockHitResult
                if (hit.type == HitResult.Type.BLOCK) {
                    val blockState = context.source.world!!.getBlockState(hit.blockPos)
                    logger.info(blockState.block.toString())
                    logger.info(blockState.properties.toString())
                    logger.info("")

                    val itemStack = ItemStack(blockState.block.asItem())

                    val nbt = NbtCompound()

                    blockState.properties.forEach { property ->
                        logger.info("${property.name}: ${blockState.get(property)}")
                        nbt.putString(property.name, blockState.get(property).toString())
                    }

                    itemStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt))
                    itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)

                    context.source.player!!.inventory.insertStack(itemStack)

                    val a: ItemStack = blockState.block.getPickStack(context.source.world, hit.blockPos, blockState)

                    context.source.player!!.inventory.insertStack(a)
                }
                1
            }
        )
    }
}