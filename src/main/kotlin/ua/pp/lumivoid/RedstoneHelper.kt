package ua.pp.lumivoid

import net.fabricmc.api.ModInitializer
import ua.pp.lumivoid.registration.CommandsRegistration
import ua.pp.lumivoid.registration.EachTickRegistration
import ua.pp.lumivoid.registration.PacketReceiverRegistration
import ua.pp.lumivoid.util.VersionChecker


@Suppress("unused")
object RedstoneHelper : ModInitializer {
	private val logger = Constants.LOGGER

	override fun onInitialize() {
		logger.info("Hello from Bumer_32!")
		logger.info("Initializing ${Constants.MOD_ID}!")

		logger.info(VersionChecker.checkRedstoneHelperVersionString())

		CommandsRegistration.register() // Registering commands
		PacketReceiverRegistration.register() // Registering packets
		EachTickRegistration.register() // Register something for each tick
	}
}
