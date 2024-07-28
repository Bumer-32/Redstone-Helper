package ua.pp.lumivoid.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.network.SendPacket
import ua.pp.lumivoid.network.packets.c2s.QuickTeleportC2SPacket

object QuickTpKeyBinding {
    private val logger = Constants.LOGGER

    private val quickTpKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding("redstone-helper.keybinding.key.quickTp",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "redstone-helper.keybinding.category.basic"
        )
    )

    fun register() {
        logger.debug("Registering QuickTpKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (quickTpKeyBinding.wasPressed()) {
                if (client.player!!.commandSource.hasPermissionLevel(2)) {
                    val config = Config()
                    SendPacket.sendPacket(QuickTeleportC2SPacket(config.quickTpDistance, config.quickTpIncludeFluids, Constants.aMinecraftClass))
                } else {
                    HudToast.addToastToQueue(Text.translatable("redstone-helper.stuff.info.error.no_permission"), true)
                }
            }
        }
    }
}