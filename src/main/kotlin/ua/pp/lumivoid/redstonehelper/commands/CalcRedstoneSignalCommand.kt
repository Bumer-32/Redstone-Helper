@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.util.Calculate
import kotlin.random.Random

object CalcRedstoneSignalCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>, registryAccess: CommandRegistryAccess) {
        logger.debug("/calc-redstone-signal: Registering calc-redstone-signal command")

        dispatcher.register(ClientCommandManager.literal("calc-redstone-signal")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                logger.debug("/redstone-give-signal: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(ClientCommandManager.argument("signal", IntegerArgumentType.integer(1, 15))
                .executes { context ->
                    logger.debug("/calc-redstone-signal: Calculating redstone signal from chat")
                    execute(context, Items.WOODEN_SHOVEL, IntegerArgumentType.getInteger(context, "signal"))
                    1
                }
                .then(ClientCommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                    .executes { context ->
                        logger.debug("/calc-redstone-signal: Calculating redstone signal from chat with item")
                        execute(context, ItemStackArgumentType.getItemStackArgument(context, "item").item, IntegerArgumentType.getInteger(context, "signal"))
                        1
                    }
                )
            )
        )
    }

    private fun execute(context: CommandContext<FabricClientCommandSource>, item: Item, redstoneSignal: Int) {
        if (redstoneSignal == 0) {
            val funnyInt = Random.nextInt(1, Constants.LOCALIZEIDS.Counts.FUNNY_COUNT + 1)
            HudToast.addToastToQueue(Text.translatable("redstone-helper.stuff.funny.$funnyInt"), false)
        } else {
            val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!
            if (hit.type == Type.BLOCK) {
                val blockHit = hit as BlockHitResult
                val blockPos = blockHit.blockPos
                try {
                    val blockInventory: Inventory = context.source.client.world!!.getBlockEntity(blockPos) as Inventory
                    val amount = Calculate.calculateRedstoneSignal(redstoneSignal, item, blockInventory.size())

                    logger.debug("/calc-redstone-signal: Calculated redstone signal: $amount, item: ${item.name}")
                    context.source.sendFeedback(
                        Text.translatable(Constants.LOCALIZEIDS.FEATURE_CALCREDSTONESIGNAL_CALCULATEDSIGNAL, amount, item.name)
                    )
                } catch (e: NullPointerException) {
                    logger.debug("/calc-redstone-signal: Failed to get block inventory at $blockPos, think it`s not a block entity with inventory")
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_INVALIDBLOCKINVENTORY))
                }
            } else {
                logger.debug("/calc-redstone-signal: No block in crosshair target")
                HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_BLOCKNOTFOUND))
            }
        }
    }
}


