package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.network.packet.s2c.play.PositionFlag
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import ua.pp.lumivoid.Constants
import java.util.*

object QuickTpCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        logger.debug("/quicktp: Registering autowire command")

        dispatcher.register(CommandManager.literal("quicktp")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                execute(context)
                1
            }
            .then(CommandManager.argument("distance", IntegerArgumentType.integer(1, 1000))
                .executes { context ->
                    execute(context, IntegerArgumentType.getInteger(context, "distance").toDouble())
                    1
                }
                .then(CommandManager.argument("includeFluids", BoolArgumentType.bool())
                    .executes { context ->
                        execute(context, IntegerArgumentType.getInteger(context, "distance").toDouble(), BoolArgumentType.getBool(context, "includeFluids"))
                        1
                    }
                )
            )
        )
    }

    private fun execute(context: CommandContext<ServerCommandSource>, distance: Double = 50.0, includeFluids: Boolean = false) {
        logger.debug("/quicktp: Teleporting player")

        val hit = MinecraftClient.getInstance().cameraEntity!!.raycast(distance, 1.0F, includeFluids)

        val player = context.source.player
        val target = player as Entity
        target.teleport(context.source.server.worlds.first(), hit.pos.x, hit.pos.y, hit.pos.z, EnumSet.noneOf(PositionFlag::class.java), player.getHeadYaw(), player.pitch)
    }
}