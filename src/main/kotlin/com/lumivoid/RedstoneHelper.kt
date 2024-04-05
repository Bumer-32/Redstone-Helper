package com.lumivoid

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger("redstone-helper")

	override fun onInitialize() {
		logger.info("Initializing redstone helper!")
	}
}