package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants

object CustomQuickTpCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/customquicktp: Registering autowire command")

        dispatcher.register(ClientCommandManager.literal("customquicktp")
            .requires { source -> source.hasPermissionLevel(2) }
            .executes { context ->
                val config = Config()
                execute(context, config.customQuickTpDistance.toDouble(), config.customQuickTpIncludeFluids)
                1
            }
            .then(ClientCommandManager.argument("distance", IntegerArgumentType.integer(1, 1000))
                .executes { context ->
                    execute(context, IntegerArgumentType.getInteger(context, "distance").toDouble(), Config().customQuickTpIncludeFluids)
                    1
                }
                .then(ClientCommandManager.argument("includeFluids", BoolArgumentType.bool())
                    .executes { context ->
                        execute(context, IntegerArgumentType.getInteger(context, "distance").toDouble(), BoolArgumentType.getBool(context, "includeFluids"))
                        1
                    }
                )
            )
        )
    }

    private fun execute(context: CommandContext<FabricClientCommandSource?>, distance: Double, includeFluids: Boolean) {
        if (!ClientOptions.illegalFeatureNotified) {
            context.source!!.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_WARNING_ILLEGALFEATURE))
            ClientOptions.illegalFeatureNotified = true // Only 1 time on world login
        }
        val hit = MinecraftClient.getInstance().cameraEntity!!.raycast(distance, 1.0F, includeFluids)
        MinecraftClient.getInstance().player!!.networkHandler.sendCommand("tp @s ${hit.pos.x} ${hit.pos.y} ${hit.pos.z}")
    }
}