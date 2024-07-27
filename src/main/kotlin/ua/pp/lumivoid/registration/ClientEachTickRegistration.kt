package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import ua.pp.lumivoid.Constants

object ClientEachTickRegistration {
    private val logger = Constants.LOGGER

    fun register() {
        ClientTickEvents.END_CLIENT_TICK.register {
//            for (key in GLFW.GLFW_KEY_SPACE..GLFW.GLFW_KEY_LAST) {
//                if (InputUtil.isKeyPressed(MinecraftClient.getInstance()!!.window.handle, key)) {
//                    Macros.listMacros().forEach { macro: Macro ->
//                        if (macro.key == key && macro.enabled) {
//                            macro.commands.forEach { command ->
//                                logger.debug("/macro: Executing command: $command")
//                                if (command.startsWith("/")) {
//                                    MinecraftClient.getInstance().player!!.networkHandler.sendCommand(command.replaceFirst("/", ""))
//                                } else {
//                                    MinecraftClient.getInstance().player!!.networkHandler.sendCommand(command)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}