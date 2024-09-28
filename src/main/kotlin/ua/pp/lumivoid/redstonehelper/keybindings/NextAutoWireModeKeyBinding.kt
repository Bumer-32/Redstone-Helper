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

object NextAutoWireModeKeyBinding {
    private val logger = Constants.LOGGER

    private val nextAutoWireKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(Constants.LOCALIZEIDS.KEYBINDING_KEY_NEXTAUTOWIREMODE,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD,
            Constants.LOCALIZEIDS.KEYBINDING_CATEGORY_BASIC
        )
    )

    fun register() {
        logger.debug("Registering NextAutoWireModeKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (nextAutoWireKeyBinding.wasPressed()) {
                if (client.player!!.commandSource.hasPermissionLevel(2)) {
                    AutoWire.setMode(AutoWire.next(ClientOptions.autoWireMode))
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AUTOWIRE_CURRENTAUTOWIREMODE, Text.translatable("redstone-helper.feature.auto_wire.modes.${ClientOptions.autoWireMode.toString().lowercase()}")), true)
                } else {
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_NOPERMISSION), true)
                }
            }
        }
    }
}