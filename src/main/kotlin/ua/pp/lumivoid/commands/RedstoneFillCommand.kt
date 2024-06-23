package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.minecraft.client.MinecraftClient
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type


object RedstoneFillCommand {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>, registryAccess: CommandRegistryAccess) {
        dispatcher.register(CommandManager.literal("redstone-fill")
            .requires { source -> source.hasPermissionLevel(2) }
            .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                .then(CommandManager.argument("count", IntegerArgumentType.integer(1, 3456))
                    .executes { context ->
                        val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!

                        if (hit.type == Type.BLOCK) {
                            val blockHit = hit as BlockHitResult
                            val blockPos = blockHit.blockPos

                            try {
                                val blockInventory: Inventory = context.source.server.worlds.first().getBlockEntity(blockPos) as Inventory // Omg I can get server world from context
                                blockInventory.clear()

                                val item = ItemStackArgumentType.getItemStackArgument(context, "item").item
                                var amount = IntegerArgumentType.getInteger(context, "count")

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
                        1
                    }
                )
            )
        )
    }
}