@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.redstonehelper.commands

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
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.FillInventoryC2SPacket
import ua.pp.lumivoid.redstonehelper.util.Calculate
import kotlin.random.Random


object RedstoneFillSignalCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>, registryAccess: CommandRegistryAccess) {
        logger.debug("/redstone-fill-signal: Registering redstone-fill-signal command")

        dispatcher.register(ClientCommandManager.literal("redstone-fill-signal")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                logger.debug("/redstone-fill-signal: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(ClientCommandManager.argument("signal", IntegerArgumentType.integer(0, 15))
                .executes { context ->
                    execute(context, Items.WOODEN_SHOVEL, IntegerArgumentType.getInteger(context, "signal"))
                    1
                }
                .then(
                    ClientCommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                    .executes { context ->
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
            logger.debug("/redstone-fill-signal: Trying to fill inventory with blocks by signal")

            val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!
            if (hit.type == Type.BLOCK) {
                val blockHit = hit as BlockHitResult
                val blockPos = blockHit.blockPos

                try {
                    val blockInventory: Inventory = context.source.world.getBlockEntity(blockPos) as Inventory // Omg I can get server world from context
                    val amount = Calculate.calculateRedstoneSignal(redstoneSignal, item, blockInventory.size())

                    logger.debug("/redstone-fill-signal: Calculated amount of items: $amount")

                    SendPacket.sendPacket(FillInventoryC2SPacket(blockPos, Registries.ITEM.getId(item), amount, Constants.aMinecraftClass))
                } catch (e: NullPointerException) {
                    logger.debug("/redstone-fill: Failed to get block inventory at $blockPos, think it`s not a block entity with inventory")
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_INVALIDBLOCKINVENTORY))
                }
            } else {
                logger.debug("/calc-redstone-signal: No block in crosshair target")
                HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_BLOCKNOTFOUND))
            }
        }
    }
}