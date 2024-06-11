package ua.pp.lumivoid.commands

import ua.pp.lumivoid.Constants
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import ua.pp.lumivoid.util.SendPackage

object RedstoneHelperCommand {
    private val commandsList = setOf( // Not sure is it ok to keep commands list here, but IT'S MY MOD! I CAN DO WHAT I WANT
        "/restone-helper",
        "/calc",
        "/autowire   //works only in creative",
        "/instaLamp   //works only in creative and needs mod on server"
        )
    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("redstone-helper")
            .then(ClientCommandManager.literal("help")
                .executes { context ->
                    context.source.sendFeedback(Text.translatable("help.redstone-helper.help"))
                    for (command in commandsList) {
                        context.source.sendFeedback(Text.literal(command))
                    }
                    1
                }
            )
            .then(ClientCommandManager.literal("version")
                .executes { context ->
                    context.source.sendFeedback(Text.translatable("help.redstone-helper.version", Constants.MOD_VERSION))
                    1
                }
            )
        )
    }
}