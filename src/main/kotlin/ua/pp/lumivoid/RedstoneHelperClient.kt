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
		ClientPacketReceiverRegistration.register() // Registering packet receiver

		// Sync config screen
		Config()

		// Adding toast to hud
		Hud.add(Constants.TOAST_ID) { Containers.verticalFlow(Sizing.content(), Sizing.content())
			.child(
				Components.label(Text.literal("Your ad here"))
					.id("text")
			)
			.surface(Surface.flat(0x77000000).and(Surface.outline(-0xededee)))
			.padding(Insets.of(5))
			.positioning(Positioning.relative(155, 35))
		}
	}
}