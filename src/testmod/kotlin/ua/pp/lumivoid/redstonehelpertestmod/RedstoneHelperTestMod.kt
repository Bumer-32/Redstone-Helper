package ua.pp.lumivoid.redstonehelpertestmod

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient


@Suppress("unused")
object RedstoneHelperTestMod : ModInitializer {
	private val logger = Constants.LOGGER

	override fun onInitialize() {
		logger.info("Initializing ${Constants.MOD_ID}!")
	}

	fun endOfTest() {
		logger.info("End of test!")

		val scope = CoroutineScope(Dispatchers.Default)

		logger.info("Minecraft will be stopped in 5 seconds")

		scope.launch {
			Thread.sleep(5000)

			MinecraftClient.getInstance().scheduleStop()
		}
	}
}
