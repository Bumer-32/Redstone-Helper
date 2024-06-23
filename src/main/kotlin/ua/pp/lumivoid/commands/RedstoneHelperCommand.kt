package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants

object RedstoneHelperCommand {
    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        dispatcher.register(ClientCommandManager.literal("redstone-helper")
            .then(ClientCommandManager.literal("help")
                .executes { context ->
                    context.source.sendFeedback(Text.translatable("help.redstone-helper.help"))
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