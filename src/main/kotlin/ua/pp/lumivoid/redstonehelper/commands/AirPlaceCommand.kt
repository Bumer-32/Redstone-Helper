package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Constants

object AirPlaceCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/airplace: Registering airplace command")

        dispatcher.register(ClientCommandManager.literal("airplace")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context ->
                ClientOptions.isAirPlaceEnabled = !ClientOptions.isAirPlaceEnabled
                logger.debug("/airplace: Switch airplace state to: ${ClientOptions.isAirPlaceEnabled}")
                if (ClientOptions.isAirPlaceEnabled) {
                    context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AIRPLACE_AIRPLACEON))
                } else {
                    context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AIRPLACE_AIRPLACEOFF))
                }
                1
            }
            .then(
                ClientCommandManager.literal("state")
                .executes { context ->
                    logger.debug("/airplace: Current airplace state: ${ClientOptions.isAirPlaceEnabled}")
                    if (ClientOptions.isAirPlaceEnabled) {
                        context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AIRPLACE_AIRPLACEON))
                    } else {
                        context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AIRPLACE_AIRPLACEOFF))
                    }
                    1
                }
            )
        )
    }
}