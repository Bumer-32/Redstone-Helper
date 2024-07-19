package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.util.TickHandler
import ua.pp.lumivoid.util.VersionChecker

object LogginedInEvent {
    fun register() {
        ClientLoginConnectionEvents.INIT.register { _, _ ->
            val config = Config()

            //check for mod updates
            TickHandler.scheduleAction(100) { // after 5 secs to load hud
                VersionChecker.checkRedstoneHelperVersionWithToast()
            }

            ClientOptions.autoWireMode = config.defaultAutoWireMode
        }
    }
}