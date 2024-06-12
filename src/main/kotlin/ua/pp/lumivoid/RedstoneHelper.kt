package ua.pp.lumivoid

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import ua.pp.lumivoid.registration.CommandsRegistration
import ua.pp.lumivoid.registration.EachTickRegistration
import ua.pp.lumivoid.registration.PacketReceiverRegistration


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

	override fun onInitialize() {
		logger.info("Initializing ${Constants.MOD_ID}!")

		CommandsRegistration.register() // Registering commands
		PacketReceiverRegistration.register() // Registering packets
		EachTickRegistration.register() // Register something for each tick
	}
}