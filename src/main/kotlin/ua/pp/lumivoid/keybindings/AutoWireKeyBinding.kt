package ua.pp.lumivoid.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.ClientOptions

object AutoWireKeyBinding {
    private val autoWireKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("key.redstone-helper.enable_autowire_keybinding",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "key.redstone-helper.category.basic"
        )
    )

    fun register() {
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (autoWireKeyBinding.wasPressed()) {
                ClientOptions.isAutoWireEnabled = !ClientOptions.isAutoWireEnabled
                if (ClientOptions.isAutoWireEnabled) {
                    MinecraftClient.getInstance().inGameHud.chatHud.addMessage(Text.translatable("info.redstone-helper.auto_wire_on"))
                } else {
                    MinecraftClient.getInstance().inGameHud.chatHud.addMessage(Text.translatable("info.redstone-helper.auto_wire_off"))
                }
            }
        }
    }
}