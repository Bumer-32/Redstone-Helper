package ua.pp.lumivoid.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.CalcScreen

object CalcKeyBinding {
    private val logger = Constants.LOGGER

    private val calcKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.redstone-helper.calc_keybinding",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "key.redstone-helper.category.basic"
        )
    )

    fun register() {
        logger.info("Registering CalcKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (calcKeyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(CalcScreen())
            }
        }
    }
}