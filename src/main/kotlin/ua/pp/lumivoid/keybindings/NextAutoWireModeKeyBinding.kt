package ua.pp.lumivoid.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.util.features.AutoWire

object NextAutoWireModeKeyBinding {
    private val logger = Constants.LOGGER

    private val nextAutoWireKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.redstone-helper.next_autowire_mode_keybinding",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD,
            "key.redstone-helper.category.basic"
        )
    )

    fun register() {
        logger.debug("Registering NextAutoWireModeKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (nextAutoWireKeyBinding.wasPressed()) {
                ClientOptions.autoWireMode = AutoWire.next(ClientOptions.autoWireMode)
                HudToast.addToastToQueue(Text.translatable("gui.redstone-helper.current_auto_wire_mode", Text.translatable("gui.redstone-helper.${ClientOptions.autoWireMode.toString().lowercase()}")), true)
            }
        }
    }
}