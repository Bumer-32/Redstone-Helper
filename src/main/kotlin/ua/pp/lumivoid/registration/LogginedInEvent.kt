package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.minecraft.client.MinecraftClient
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.util.VersionChecker

object LogginedInEvent {
    fun register() {
        ClientLoginConnectionEvents.INIT.register { _, _ ->
            //check for mod updates
            if (Config().enableUpdateCheck) {
                MinecraftClient.getInstance().inGameHud.chatHud.addMessage(VersionChecker.checkRedstoneHelperVersionLocalized())
            }

            ClientOptions.autoWireMode = Config().defaultAutoWireMode
        }
    }
}