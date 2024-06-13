package ua.pp.lumivoid

import net.fabricmc.api.ClientModInitializer
import org.mariuszgromada.math.mxparser.License
import org.slf4j.LoggerFactory
import ua.pp.lumivoid.registration.AutoWireRegistration
import ua.pp.lumivoid.registration.ClientCommandsRegistration
import ua.pp.lumivoid.registration.KeyBindingsRegistration
import ua.pp.lumivoid.registration.LogginedInEvent


object RedstoneHelperClient : ClientModInitializer {
	private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

	override fun onInitializeClient() {
		logger.info("Initializing client ${Constants.MOD_ID}")
		License.iConfirmNonCommercialUse("Artem")

        ClientCommandsRegistration.register() // Registering client commands
        KeyBindingsRegistration.register() // Registering keybindings
		AutoWireRegistration.register() // Registering autowire function
		LogginedInEvent.register() // For mod updates check

	}
}