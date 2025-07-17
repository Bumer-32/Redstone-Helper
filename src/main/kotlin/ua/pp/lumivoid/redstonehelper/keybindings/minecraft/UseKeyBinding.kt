package ua.pp.lumivoid.redstonehelper.keybindings.minecraft

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Constants

object UseKeyBinding {
    val logger = Constants.LOGGER

    private var lastBlockPlacementTime = 0L

    fun register() {
        logger.debug("Registering UseKeyBinding")

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (client.options.useKey.isPressed && ClientOptions.isAirPlaceEnabled) {
                val hit = client.crosshairTarget!! as BlockHitResult

                if (hit.type == HitResult.Type.MISS) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastBlockPlacementTime >= 400) {

                        client.player!!.swingHand(client.player!!.activeHand)
                        client.networkHandler!!.sendPacket(PlayerInteractBlockC2SPacket(client.player!!.activeHand, hit, 1))
                        lastBlockPlacementTime = currentTime

                        // Autowire
                        if (ClientOptions.isAutoWireEnabled) {
                            ClientOptions.autoWireMode.place(hit.blockPos, client.player!!, client.player!!.world)
                        }
                    }
                }
            }
        }
    }
}