package com.lumivoid

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.registry.Registries
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import org.slf4j.LoggerFactory


object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger(Constants().MOD_ID)

	override fun onInitialize() {
		logger.info("Initializing ${Constants().MOD_ID}!")

		Commands().register() // Registering commands

		// SET_BLOCK_PACKET receive
		ServerPlayNetworking.registerGlobalReceiver(Constants().SET_BLOCK_PACKET) {
			server: MinecraftServer?,
			player: ServerPlayerEntity?,
			handler: ServerPlayNetworkHandler?,
			buf: PacketByteBuf?,
			sender: PacketSender? ->

			val pos = buf?.readBlockPos()
			val blockToSet = Registries.BLOCK.get(buf?.readIdentifier())

			server!!.execute {
				player!!.serverWorld.setBlockState(pos, blockToSet.defaultState)
			}
		}
	}
}