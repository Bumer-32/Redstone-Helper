package ua.pp.lumivoid.keybindings

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.gui.CalcScreen

object CalcKeyBinding {
    private val calcKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.redstone-helper.calc_keybinding",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "key.redstone-helper.category.basic"
        )
    )

    fun register() {
        while (calcKeyBinding.wasPressed()) {
            MinecraftClient.getInstance().setScreen(CalcScreen())
        }
    }
}