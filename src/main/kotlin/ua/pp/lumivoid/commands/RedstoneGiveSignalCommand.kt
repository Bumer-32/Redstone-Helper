@file:Suppress("LoggingSimilarMessage", "DuplicatedCode")

package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.BlockStateArgumentType
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.Calculate

object RedstoneGiveSignalCommandCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess) {
        logger.debug("/redstone-give-signal: Registering redstone-give command")

        val redstoneGiveSignalNode = dispatcher.register(CommandManager.literal("redstone-give-signal")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                logger.debug("/redstone-give-signal: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LocalizeIds.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(CommandManager.argument("signal", IntegerArgumentType.integer(0, 15))
                .executes { context ->
                    execute(context, IntegerArgumentType.getInteger(context, "signal"), Items.WOODEN_SHOVEL, Items.BARREL)
                    1
                }
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                    .executes { context ->
                        execute(context, IntegerArgumentType.getInteger(context, "signal"), ItemStackArgumentType.getItemStackArgument(context, "item").item, Items.BARREL)
                        1
                    }
                    .then(CommandManager.argument("block", BlockStateArgumentType.blockState(registryAccess))
                        .executes { context ->
                            execute(context, IntegerArgumentType.getInteger(context, "signal"), ItemStackArgumentType.getItemStackArgument(context, "item").item, BlockStateArgumentType.getBlockState(context, "block").blockState.block.asItem())
                            1
                        }
                    )
                )
            )
        )

        dispatcher.register(CommandManager.literal("ss").redirect(redstoneGiveSignalNode))
    }

    private fun execute(context: CommandContext<ServerCommandSource>, redstoneSignal: Int, item: Item, block: Item) {
        logger.debug("/redstone-give-signal: Trying to give blockEntity")

        var inventorySize = 0

        val blockID = Registries.ITEM.getId(block)

        when (blockID) { // I don't want to do this but there's no way
            Identifier.of("minecraft:hopper") -> {
                inventorySize = 5
            }
            Identifier.of("minecraft:chest") -> {
                inventorySize = 27
            }
            Identifier.of("minecraft:barrel") -> {
                inventorySize = 27
            }
            Identifier.of("minecraft:furnace") -> {
                inventorySize = 3
            }
            Identifier.of("minecraft:smoker") -> {
                inventorySize = 3
            }
            Identifier.of("minecraft:blast_furnace") -> {
                inventorySize = 3
            }
            Identifier.of("minecraft:dropper") -> {
                inventorySize = 9
            }
            Identifier.of("minecraft:dispenser") -> {
                inventorySize = 9
            }
            else -> {
                context.source.sendError(Text.translatable(Constants.LocalizeIds.STUFF_INFO_ERROR_INVALIDBLOCKINVENTORY))
                return
            }
        } // Yeah yeah, shit code

        var amount = Calculate.calculateRedstoneSignal(redstoneSignal, item, inventorySize)
        val blockItemStack = ItemStack(block)

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
