package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.loader.api.FabricLoader
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.commands.ColorcodeCommand
import ua.pp.lumivoid.commands.RedstoneGiveCommand
import ua.pp.lumivoid.commands.RedstoneGiveSignalCommandCommand

object CommandsRegistration {
    private val logger = Constants.LOGGER

    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            RedstoneGiveCommand.register(dispatcher, registryAccess)
            RedstoneGiveSignalCommandCommand.register(dispatcher, registryAccess)

            if (FabricLoader.getInstance().isModLoaded(Constants.WORLD_EDIT_FABRIC_ID)) {
                logger.info("WorldEdit found, registering commands")

                ColorcodeCommand.register(dispatcher)
            }
        }
    }
}