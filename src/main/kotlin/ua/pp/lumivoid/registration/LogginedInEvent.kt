package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.util.VersionChecker

object LogginedInEvent {
    fun register() {
        ClientLoginConnectionEvents.INIT.register { _, _ ->
            //check for mod updates
            MinecraftClient.getInstance().inGameHud.chatHud.addMessage(VersionChecker.checkRedstoneHelperVersionLocalized())
        }
    }
}