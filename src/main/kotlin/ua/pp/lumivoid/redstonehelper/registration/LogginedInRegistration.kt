package ua.pp.lumivoid.redstonehelper.registration

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.util.JsonConfig
import ua.pp.lumivoid.redstonehelper.util.TickHandler
import ua.pp.lumivoid.redstonehelper.util.VersionChecker
import kotlin.random.Random

object LogginedInRegistration {
    fun register() {
        ClientLoginConnectionEvents.INIT.register { _, _ ->
            val config = Config()

            //check for mod updates
            TickHandler.scheduleAction(100) { // after 5 secs to load hud
                VersionChecker.checkRedstoneHelperVersionWithToast()
            }

            if (config.enableHints) {
                TickHandler.scheduleAction(300) { // 15 secs, hints
                    val hintInt = Random.nextInt(1, Constants.LOCALIZEIDS.Counts.HINTS_COUNT + 1)
                    HudToast.addToastToQueue(Text.translatable("redstone-helper.feature.hints.hint.$hintInt"), false)
                }
            }

            if (config.rememberLastAutoWireMode) {
                ClientOptions.autoWireMode = JsonConfig.readConfig().autoWireMode ?: config.defaultAutoWireMode
            } else {
                ClientOptions.autoWireMode = config.defaultAutoWireMode
            }

            // Notifications for EVERY server or world
            ClientOptions.illegalFeatureNotified = false
        }
    }
}