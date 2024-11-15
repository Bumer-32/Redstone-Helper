package ua.pp.lumivoid.redstonehelper.keybindings

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.QuickTeleportC2SPacket

object QuickTpKeyBinding {
    private val logger = Constants.LOGGER

    private val quickTpKeyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(Constants.LOCALIZEIDS.KEYBINDING_KEY_QUICKTP,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            Constants.LOCALIZEIDS.KEYBINDING_CATEGORY_BASIC
        )
    )

    fun register() {
        logger.debug("Registering QuickTpKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (quickTpKeyBinding.wasPressed()) {
                if (client.player!!.hasPermissionLevel(2)) {
                    val config = Config()
                    SendPacket.sendPacket(QuickTeleportC2SPacket(config.quickTpDistance, config.quickTpIncludeFluids, Constants.aMinecraftClass))
                } else {
                    HudToast.addToastToQueue(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_NOPERMISSION), true)
                }
            }
        }
    }
}