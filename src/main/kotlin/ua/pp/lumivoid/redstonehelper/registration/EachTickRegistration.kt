package ua.pp.lumivoid.registration

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import ua.pp.lumivoid.util.TickHandler

object EachTickRegistration {
    fun register() {
        ServerTickEvents.END_SERVER_TICK.register {
            TickHandler.onEndTick()
        }
    }
}