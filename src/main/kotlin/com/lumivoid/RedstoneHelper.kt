package com.lumivoid

import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import org.slf4j.LoggerFactory

object RedstoneHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger("redstone-helper")

	override fun onInitialize() {
		logger.info("Initializing redstone helper!!")

		CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
			dispatcher.register(literal("calc")
				.then(argument("value", IntegerArgumentType.integer())
					.executes { context ->
						val value1 = IntegerArgumentType.getInteger(context, "value")
						context.getSource().sendFeedback({ net.minecraft.text.Text.literal(value1.toString()) }, false)
						1
					}
				))
		}
	}
}