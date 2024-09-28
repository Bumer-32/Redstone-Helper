@file:Suppress("LoggingSimilarMessage", "DuplicatedCode")

package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.BlockStateArgumentType
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import kotlin.random.Random

object RedstoneGiveCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess) {
        logger.debug("/redstone-give: Registering redstone-give command")

        dispatcher.register(CommandManager.literal("redstone-give")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                logger.debug("/redstone-give: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(CommandManager.argument("count", IntegerArgumentType.integer(0, 1728))
                .executes { context ->
                    logger.debug("/redstone-give: Missing arguments!")
                    context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                    1
                }
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                    .executes { context ->
                        execute(context, IntegerArgumentType.getInteger(context, "count"), ItemStackArgumentType.getItemStackArgument(context, "item").item, Blocks.BARREL)
                        1
                    }
                    .then(CommandManager.argument("block", BlockStateArgumentType.blockState(registryAccess))
                        .executes { context ->
                            execute(context, IntegerArgumentType.getInteger(context, "count"), ItemStackArgumentType.getItemStackArgument(context, "item").item, BlockStateArgumentType.getBlockState(context, "block").blockState.block)
                            1
                        }
                    )
                )
            )
        )
    }

    private fun execute(context: CommandContext<ServerCommandSource>, count: Int, item: Item, block: Block) {
        if (count == 0) {
            val funnyInt = Random.nextInt(1, Constants.LOCALIZEIDS.Counts.FUNNY_COUNT + 1)
            HudToast.addToastToQueue(Text.translatable("redstone-helper.stuff.funny.$funnyInt"), false)
        } else {
            logger.debug("/redstone-give: Trying to give blockEntity")

            var amount = count
            val blockItemStack = ItemStack(block.asItem())

            val items = mutableListOf<ItemStack>()

            if (item.maxCount == 1) {
                for (i in 0 until amount) {
                    items.add(ItemStack(item))
                }
            } else {
                for (i in 0 until amount / item.maxCount + 1) {
                    if (amount >= item.maxCount) {
                        items.add(ItemStack(item, item.maxCount))
                        amount -= item.maxCount
                    } else if (amount > 0) {
                        items.add(ItemStack(item, amount))
                    }
                }
            }

            blockItemStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(items))
            blockItemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
            context.source.player!!.inventory.insertStack(blockItemStack)
        }
    }
}
