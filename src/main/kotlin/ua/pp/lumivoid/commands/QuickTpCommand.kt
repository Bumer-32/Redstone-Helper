package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.packets.QuickTeleportPacket
import ua.pp.lumivoid.util.SendPacket

object QuickTpCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/quicktp: Registering autowire command")

        dispatcher.register(
            ClientCommandManager.literal("quicktp")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes {
                val config = Config()
                SendPacket.sendPacket(QuickTeleportPacket(config.quickTpDistance, config.quickTpIncludeFluids, Constants.aMinecraftClass))
                1
            }
            .then(ClientCommandManager.argument("distance", IntegerArgumentType.integer(1, 1000))
                .executes { context ->
                    SendPacket.sendPacket(QuickTeleportPacket(IntegerArgumentType.getInteger(context, "distance"), Config().quickTpIncludeFluids, Constants.aMinecraftClass))
                    1
                }
                .then(ClientCommandManager.argument("includeFluids", BoolArgumentType.bool())
                    .executes { context ->
                        SendPacket.sendPacket(QuickTeleportPacket(IntegerArgumentType.getInteger(context, "distance"), BoolArgumentType.getBool(context, "includeFluids"), Constants.aMinecraftClass))
                        1
                    }
                )
            )
        )
    }
}