package ua.pp.lumivoid

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World
import org.slf4j.LoggerFactory


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)

	override fun onInitialize() {
		logger.info("Initializing ${Constants.MOD_ID}!")

		Commands.register() // Registering commands
		PacketReceiver.register() // Registering packets

//		UseBlockCallback.EVENT.register { player: PlayerEntity, world: World, hand: Hand, hitResult: HitResult ->
//			this.logger.info(player, world, hand, hitResult)
//			1
//		}
	}
}