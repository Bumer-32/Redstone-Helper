package com.lumivoid

import com.lumivoid.gui.CalcGui
import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

class KeyBindings {
    private val calcKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.redstone-helper.calc_keybinding",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "key.redstone-helper.category.basic"
        )
    )

    fun register() {
        ClientTickEvents.END_CLIENT_TICK.register{ client ->
            while (calcKeyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(CottonClientScreen(CalcGui()))
            }
        }
    }
}