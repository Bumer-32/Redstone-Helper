package com.lumivoid

import net.fabricmc.api.ClientModInitializer
import org.mariuszgromada.math.mxparser.License
import org.slf4j.LoggerFactory


object RedstoneHelperClient : ClientModInitializer {
	private val logger = LoggerFactory.getLogger("redstone-helper")

	override fun onInitializeClient() {
		logger.info("Initializing Client Redstone Helper")
		License.iConfirmNonCommercialUse("Artem")

		Commands().register() // Registering commands
		KeyBindings().register() // Registering keybindings
	}
}