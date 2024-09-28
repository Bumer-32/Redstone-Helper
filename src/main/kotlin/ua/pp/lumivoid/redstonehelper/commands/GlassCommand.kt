package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.SetPlayerSlotInventoryC2SPacket

object GlassCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/glass: Registering Glass Command")

        dispatcher.register(
            ClientCommandManager.literal("glass")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                val player = context.source.player!!
                val itemStack = player.inventory.getStack(player.inventory.selectedSlot)


                if (Registries.ITEM.getId(itemStack.item).toString().contains("glass")) {
                    SendPacket.sendPacket(SetPlayerSlotInventoryC2SPacket(player.inventory.selectedSlot, ClientOptions.rememberLastSelectedItems!!, Constants.aMinecraftClass))
                } else {
                    ClientOptions.rememberLastSelectedItems = itemStack

                    val success = DyeColor.entries.any { dyeColor ->
                        if (Registries.ITEM.getId(itemStack.item).toString().contains(dyeColor.getName().lowercase())) {
                            val item = Registries.ITEM.get(Identifier.of("minecraft:${dyeColor.getName().lowercase()}_stained_glass"))
                            SendPacket.sendPacket(SetPlayerSlotInventoryC2SPacket(player.inventory.selectedSlot, ItemStack(item, itemStack.count), Constants.aMinecraftClass))
                            return@any true
                        }
                        false
                    }

                    if (!success) {
                        SendPacket.sendPacket(SetPlayerSlotInventoryC2SPacket(player.inventory.selectedSlot, ItemStack(Items.GLASS, itemStack.count), Constants.aMinecraftClass))
                    }
                }
                1
            }
        )
    }
}