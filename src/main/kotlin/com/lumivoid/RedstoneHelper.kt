package com.lumivoid

import com.lumivoid.commands.CalcCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import org.mariuszgromada.math.mxparser.License
import org.slf4j.LoggerFactory
import java.awt.Insets


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger("redstone-helper")

	override fun onInitialize() {
		logger.info("Initializing redstone helper!")
		License.iConfirmNonCommercialUse("Artem")

		CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
			CalcCommand().register(dispatcher)
		}
	}
}