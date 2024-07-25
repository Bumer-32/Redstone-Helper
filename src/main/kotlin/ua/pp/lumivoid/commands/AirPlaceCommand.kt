package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Constants

object AirPlaceCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/airplace: Registering instaLamp command")

        dispatcher.register(ClientCommandManager.literal("airplace")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context ->
                ClientOptions.isAirPlaceEnabled = !ClientOptions.isAirPlaceEnabled
                logger.debug("/airplace: Switch Instant Lamps state to: ${ClientOptions.isAirPlaceEnabled}")
                if (ClientOptions.isAirPlaceEnabled) {
                    context.source.sendFeedback(Text.translatable("redstone-helper.feature.airplace.airplace_on"))
                } else {
                    context.source.sendFeedback(Text.translatable("redstone-helper.feature.airplace.airplace_off"))
                }
                1
            }
            .then(
                ClientCommandManager.literal("state")
                .executes { context ->
                    logger.debug("/airplace: Current airplace state: ${ClientOptions.isAirPlaceEnabled}")
                    if (ClientOptions.isAirPlaceEnabled) {
                        context.source.sendFeedback(Text.translatable("redstone-helper.feature.airplace.airplace_on"))
                    } else {
                        context.source.sendFeedback(Text.translatable("redstone-helper.feature.airplace.airplace_off"))
                    }
                    1
                }
            )
        )
    }
}