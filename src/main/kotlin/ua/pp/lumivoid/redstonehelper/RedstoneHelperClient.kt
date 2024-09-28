package ua.pp.lumivoid

import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.container.Containers
import io.wispforest.owo.ui.core.Insets
import io.wispforest.owo.ui.core.Positioning
import io.wispforest.owo.ui.core.Sizing
import io.wispforest.owo.ui.core.Surface
import io.wispforest.owo.ui.hud.Hud
import net.fabricmc.api.ClientModInitializer
import net.minecraft.text.Text
import org.mariuszgromada.math.mxparser.License
import ua.pp.lumivoid.registration.*
import ua.pp.lumivoid.util.JsonConfig


@Suppress("unused")
object RedstoneHelperClient : ClientModInitializer {
	private val logger = Constants.LOGGER

	override fun onInitializeClient() {
		logger.info("Initializing client ${Constants.MOD_ID}")

		License.iConfirmNonCommercialUse("Artem")

        ClientCommandsRegistration.register() // Registering client commands
        KeyBindingsRegistration.register() // Registering keybindings
		AutoWireRegistration.register() // Registering autowire function
		LogginedInRegistration.register() // For mod updates check
		ClientPacketReceiverRegistration.register() // Registering packet receiver

		// ONLY FOR DOWNLOADER
		// Verify files for version
//		if (!File(Constants.CONFIG_FOLDER_PATH + "\\config.json" || JsonConfig.readConfig().modVersion != Constants.MOD_VERSION).exists()) {
//			logger.info("Other version found! cleanUp!")
//			File(Constants.CONFIG_FOLDER_PATH).listFiles()?.forEach { it.delete() }
//			JsonConfig.writeConfig(JsonConfigData(modVersion = Constants.MOD_VERSION))
//		}

		val jsonConfig = JsonConfig.readConfig()
		jsonConfig.modVersion = Constants.MOD_VERSION
		JsonConfig.writeConfig(jsonConfig)

		// Sync config screen also get config here
		val config = Config()

		// Adding toast to hud
		Hud.add(Constants.TOAST_ID) { Containers.verticalFlow(Sizing.content(), Sizing.content())
			.child(
				Components.label(Text.literal("Your ad here"))
					.id("text")
			)
			.surface(Surface.flat(0x77000000).and(Surface.outline((0xFF121212).toInt())))
			.padding(Insets.of(5))
			.positioning(Positioning.relative(config.toastPosition.hidedXPos(), config.toastPosition.yPos()))
		}
	}
}