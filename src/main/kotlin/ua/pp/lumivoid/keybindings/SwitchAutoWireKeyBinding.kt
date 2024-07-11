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

object SwitchAutoWireKeyBinding {
    private val logger = Constants.LOGGER

    private val switchAutoWireKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.redstone-helper.switch_autowire_keybinding",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "key.redstone-helper.category.basic"
        )
    )

    fun register() {
        logger.debug("Registering SwitchAutoWireKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (switchAutoWireKeyBinding.wasPressed()) {
                if (client.player!!.commandSource.hasPermissionLevel(2)) {
                    ClientOptions.isAutoWireEnabled = !ClientOptions.isAutoWireEnabled
                    if (ClientOptions.isAutoWireEnabled) {
                        HudToast.addToastToQueue(Text.translatable("info.redstone-helper.auto_wire_on"), true)
                    } else {
                        HudToast.addToastToQueue(Text.translatable("info.redstone-helper.auto_wire_off"), true)
                    }
                } else {
                    HudToast.addToastToQueue(Text.translatable("info_error.redstone-helper.no_permission"), true)
                }
            }
        }
    }
}