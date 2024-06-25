@file:Suppress("LoggingStringTemplateAsArgument", "DuplicatedCode")

package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.MinecraftClient
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type
import ua.pp.lumivoid.Constants


object RedstoneFillSignalCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>, registryAccess: CommandRegistryAccess) {
        dispatcher.register(CommandManager.literal("redstone-fill-signal")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                logger.debug("/redstone-fill-signal: Missing arguments!")
                context.source.sendError(Text.translatable("info_error.redstone-helper.missing_arguments"))
                1
            }
            .then(CommandManager.argument("signal", IntegerArgumentType.integer(1, 15))
                .executes { context ->
                    execute(context, Registries.ITEM.get(Identifier.of("minecraft:wooden_shovel")), IntegerArgumentType.getInteger(context, "signal"))
                    1
                }
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                    .executes { context ->
                        execute(context, ItemStackArgumentType.getItemStackArgument(context, "item").item, IntegerArgumentType.getInteger(context, "signal"))
                        1
                    }
                )
            )
        )
    }

    private fun execute(context: CommandContext<ServerCommandSource>, item: Item, redstoneSignal: Int) {
        logger.debug("/redstone-fill-signal: Trying to fill inventory with blocks by signal")

        val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!
        if (hit.type == Type.BLOCK) {
            val blockHit = hit as BlockHitResult
            val blockPos = blockHit.blockPos

            try {
                val blockInventory: Inventory = context.source.server.worlds.first().getBlockEntity(blockPos) as Inventory // Omg I can get server world from context
                blockInventory.clear()

                var amount = ((redstoneSignal - 1) * item.maxCount * blockInventory.size() / 14.0).toInt() + 1

                logger.debug("/redstone-fill-signal: Calculated amount of items: $amount")

                if (item.maxCount == 1) {
                    if (amount > blockInventory.size()) {
                        amount = blockInventory.size()
                    }
                    for (i in 0 until amount) {
                        blockInventory.setStack(i, ItemStack(item))
                    }
                } else {
                    if (amount > blockInventory.size() * item.maxCount) {
                        amount = blockInventory.size() * item.maxCount
                    }

                    for (i in 0 until amount / item.maxCount + 1) {
                        if (amount >= item.maxCount) {
                            blockInventory.setStack(i, ItemStack(item, item.maxCount))
                            amount -= item.maxCount
                        } else if (amount > 0) {
                            blockInventory.setStack(i, ItemStack(item, amount))
                        }
                    }
                }

                logger.debug("/redstone-fill-signal: Success!")
                context.source.sendFeedback({ Text.translatable("info.redstone-helper.success") }, false)
            } catch (e: NullPointerException) {
                logger.debug("/redstone-fill: Failed to get block inventory at $blockPos, think it`s not a block entity with inventory")
                context.source.sendError(Text.translatable("info_error.redstone-helper.invalid_block_inventory"))
            }
        }
    }
}