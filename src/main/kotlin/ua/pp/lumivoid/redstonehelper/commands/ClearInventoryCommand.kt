@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.ClearInventoryC2SPacket

object ClearInventoryCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/clear-inventory: Registering redstone-fill command")

        dispatcher.register(
            ClientCommandManager.literal("clear-inventory")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes {
                logger.debug("/clear-inventory: Trying to clear inventory!")

                val hit: HitResult = MinecraftClient.getInstance().crosshairTarget!!

                if (hit.type == Type.BLOCK) {
                    val blockHit = hit as BlockHitResult
                    val blockPos = blockHit.blockPos

                    SendPacket.sendPacket(ClearInventoryC2SPacket(blockPos, Constants.aMinecraftClass))
                } else {
                    logger.debug("/clear-inventory: No block in crosshair target")
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_BLOCKNOTFOUND))
                }

                1
            }
        )
    }
}