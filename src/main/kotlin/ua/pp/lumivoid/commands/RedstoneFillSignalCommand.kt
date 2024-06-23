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
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type


object RedstoneFillSignalCommand {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>, registryAccess: CommandRegistryAccess) {
        dispatcher.register(CommandManager.literal("redstone-fill-signal")
            .requires { source -> source.hasPermissionLevel(2) }
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
        val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!
        if (hit.type == Type.BLOCK) {
            val blockHit = hit as BlockHitResult
            val blockPos = blockHit.blockPos

            try {
                val blockInventory: Inventory = context.source.server.worlds.first().getBlockEntity(blockPos) as Inventory // Omg I can get server world from context
                blockInventory.clear()

                var amount = ((redstoneSignal - 1) * item.maxCount * blockInventory.size() / 14.0).toInt() + 1

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
            } finally {}
        }
    }
}