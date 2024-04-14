package com.lumivoid

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

	override fun onInitialize() {
		logger.info("Initializing ${Constants.MOD_ID}!")

		Commands.register() // Registering commands
		PacketReceiver.register()
	}
}