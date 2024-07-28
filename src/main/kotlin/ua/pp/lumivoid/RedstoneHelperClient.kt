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
import org.lwjgl.glfw.GLFW
import org.mariuszgromada.math.mxparser.License
import ua.pp.lumivoid.registration.*
import ua.pp.lumivoid.util.JsonConfig
import ua.pp.lumivoid.util.JsonConfigData
import ua.pp.lumivoid.util.Macro
import ua.pp.lumivoid.util.features.Macros
import java.io.File


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

		// Verify files for version
		if (!File(Constants.CONFIG_FOLDER_PATH + "\\config.json").exists() || JsonConfig.readConfig().modVersion != Constants.MOD_VERSION) {
			logger.info("Other version found! cleanUp!")
			File(Constants.CONFIG_FOLDER_PATH).listFiles()?.forEach { it.delete() }
			JsonConfig.writeConfig(JsonConfigData(modVersion = Constants.MOD_VERSION))

			Macros.addMacro(
				Macro(
					"redstoner",
					GLFW.GLFW_KEY_UNKNOWN,
					false,
					mutableListOf( // standard redstoner macro from redstone tools
						"/redstone-helper notification add-to-queue false Setting redstoner world!",
						"/gamerule doTileDrops false",
						"/gamerule doTraderSpawning false",
						"/gamerule doWeatherCycle false",
						"/gamerule doDaylightCycle false",
						"/gamerule doMobSpawning false",
						"/gamerule doContainerDrops false",
						"/time set noon",
						"/weather clear"
					)
				)
			)
		}

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