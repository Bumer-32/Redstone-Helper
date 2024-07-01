@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.client.MinecraftClient
import net.minecraft.inventory.Inventory
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type
import ua.pp.lumivoid.Constants

object ClearInventoryCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>) {
        logger.debug("/clear-inventory: Registering redstone-fill command")

        dispatcher.register(CommandManager.literal("clear-inventory")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                logger.debug("/clear-inventory: Trying to clear inventory!")

                val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!

                if (hit.type == Type.BLOCK) {
                    val blockHit = hit as BlockHitResult
                    val blockPos = blockHit.blockPos

                    try {
                        val blockInventory: Inventory = context.source.world.getBlockEntity(blockPos) as Inventory
                        blockInventory.clear()

                        logger.debug("/redstone-fill: Success!")
                        context.source.sendFeedback({ Text.translatable("info.redstone-helper.success") }, false)
                    } catch (e: NullPointerException) {
                        logger.debug("/redstone-fill: Failed to get block inventory at $blockPos, think it`s not a block entity with inventory")
                        context.source.sendError(Text.translatable("info_error.redstone-helper.invalid_block_inventory"))
                    }
                }

                1
            }
        )
    }
}