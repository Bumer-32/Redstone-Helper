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
        KeyBinding(Constants.LocalizeIds.KEYBINDING_KEY_NEXTAUTOWIREMODE,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD,
            Constants.LocalizeIds.KEYBINDING_CATEGORY_BASIC
        )
    )

    fun register() {
        logger.debug("Registering NextAutoWireModeKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (nextAutoWireKeyBinding.wasPressed()) {
                if (client.player!!.commandSource.hasPermissionLevel(2)) {
                    AutoWire.setMode(AutoWire.next(ClientOptions.autoWireMode))
                    HudToast.addToastToQueue(Text.translatable(Constants.LocalizeIds.FEATURE_AUTOWIRE_CURRENTAUTOWIREMODE, Text.translatable("redstone-helper.feature.auto_wire.modes.${ClientOptions.autoWireMode.toString().lowercase()}")), true)
                } else {
                    HudToast.addToastToQueue(Text.translatable(Constants.LocalizeIds.STUFF_INFO_ERROR_NOPERMISSION), true)
                }
            }
        }
    }
}