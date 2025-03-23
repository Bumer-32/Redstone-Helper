package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.QuickTeleportC2SPacket

object QuickTpCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/quicktp: Registering autowire command")

        dispatcher.register(ClientCommandManager.literal("quicktp")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes {
                val config = Config()
                SendPacket.sendPacket(QuickTeleportC2SPacket(config.quickTpDistance, config.quickTpIncludeFluids, Constants.aMinecraftClass))
                1
            }
            .then(ClientCommandManager.argument("distance", IntegerArgumentType.integer(1, 1000))
                .executes { context ->
                    SendPacket.sendPacket(QuickTeleportC2SPacket(IntegerArgumentType.getInteger(context, "distance"), Config().quickTpIncludeFluids, Constants.aMinecraftClass))
                    1
                }
                .then(ClientCommandManager.argument("includeFluids", BoolArgumentType.bool())
                    .executes { context ->
                        SendPacket.sendPacket(QuickTeleportC2SPacket(IntegerArgumentType.getInteger(context, "distance"), BoolArgumentType.getBool(context, "includeFluids"), Constants.aMinecraftClass))
                        1
                    }
                )
            )
        )
    }
}