package ua.pp.lumivoid

import net.fabricmc.api.ClientModInitializer
import org.mariuszgromada.math.mxparser.License
import ua.pp.lumivoid.registration.*


@Suppress("unused")
object RedstoneHelperClient : ClientModInitializer {
	private val logger = Constants.LOGGER

	override fun onInitializeClient() {
		logger.info("Initializing client ${Constants.MOD_ID}")
		License.iConfirmNonCommercialUse("Artem")

        ClientCommandsRegistration.register() // Registering client commands
        KeyBindingsRegistration.register() // Registering keybindings
		AutoWireRegistration.register() // Registering autowire function
		LogginedInEvent.register() // For mod updates check
		ClientPacketReveiverRegistration.register() // Registering packets

	}
}