package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.Options

object InstaLampCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        logger.debug("/instaLamp: Registering instaLamp command")

        dispatcher.register(CommandManager.literal("instaLamp")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context: CommandContext<ServerCommandSource> ->
                Options.isInstantLamps = !Options.isInstantLamps
                logger.debug("/instaLamp: Switch Instant Lamps state to: ${Options.isInstantLamps}")
                if (Options.isInstantLamps) {
                    context.source.sendFeedback({ Text.translatable(Constants.LocalizeIds.FEATURE_INSTALAMPS_INSTAON) }, true)
                } else {
                    context.source.sendFeedback({ Text.translatable(Constants.LocalizeIds.FEATURE_INSTALAMPS_INSTAOFF) }, true)
                }
                1
            }
            .then(CommandManager.literal("state")
                .executes { context: CommandContext<ServerCommandSource> ->
                    logger.debug("/instaLamp: Current instaLamp state: ${Options.isInstantLamps}")
                    if (Options.isInstantLamps) {
                        context.source.sendFeedback({ Text.translatable(Constants.LocalizeIds.FEATURE_INSTALAMPS_INSTAON) }, false)
                    } else {
                        context.source.sendFeedback({ Text.translatable(Constants.LocalizeIds.FEATURE_INSTALAMPS_INSTAOFF) }, false)
                    }
                    1
                }
            )
        )
    }
}