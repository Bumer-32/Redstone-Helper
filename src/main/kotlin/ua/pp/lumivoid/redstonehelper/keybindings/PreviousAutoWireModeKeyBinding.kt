package ua.pp.lumivoid.redstonehelper.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.util.features.AutoWire

object PreviousAutoWireModeKeyBinding {
    private val logger = Constants.LOGGER

    private val previousAutoWireKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(Constants.LOCALIZEIDS.KEYBINDING_KEY_PREVIOUSAUTOWIREMODE,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_COMMA,
            Constants.LOCALIZEIDS.KEYBINDING_CATEGORY_BASIC
        )
    )

    fun register() {
        logger.debug("Registering PreviousAutoWireModeKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (previousAutoWireKeyBinding.wasPressed()) {
                if (client.player!!.hasPermissionLevel(2)) {
                    AutoWire.setMode(AutoWire.previous(ClientOptions.autoWireMode))
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AUTOWIRE_CURRENTAUTOWIREMODE, Text.translatable("redstone-helper.feature.auto_wire.modes.${ClientOptions.autoWireMode.toString().lowercase()}")), true)
                } else {
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_NOPERMISSION), true)
                }
            }
        }
    }
}