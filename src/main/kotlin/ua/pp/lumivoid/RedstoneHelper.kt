package ua.pp.lumivoid

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import ua.pp.lumivoid.registration.AutoWireRegistration
import ua.pp.lumivoid.registration.CommandsRegistration
import ua.pp.lumivoid.registration.PacketReceiverRegistration


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

	override fun onInitialize() {
		logger.info("Initializing ${Constants.MOD_ID}!")

		CommandsRegistration.register() // Registering commands
		PacketReceiverRegistration.register() // Registering packets
		AutoWireRegistration.register()
	}
}
