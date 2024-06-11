package ua.pp.lumivoid

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World
import org.mariuszgromada.math.mxparser.License
import org.slf4j.LoggerFactory


object RedstoneHelperClient : ClientModInitializer {
	private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

	override fun onInitializeClient() {
		logger.info("Initializing client ${Constants.MOD_ID}")
		License.iConfirmNonCommercialUse("Artem")

        ClientCommands.register() // Registering client commands
        KeyBindings.register() // Registering keybindings

	}
}