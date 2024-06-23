package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.Options

object InstaLampCommand {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(CommandManager.literal("instaLamp")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context: CommandContext<ServerCommandSource> ->
                Options.isInstantLamps = !Options.isInstantLamps
                if (Options.isInstantLamps) {
                    context.source.sendFeedback({ Text.translatable("info.redstone-helper.insta_on") }, true)
                } else {
                    context.source.sendFeedback({ Text.translatable("info.redstone-helper.insta_off") }, true)
                }
                1
            }
            .then(CommandManager.literal("state")
                .executes { context: CommandContext<ServerCommandSource> ->
                    if (Options.isInstantLamps) {
                        context.source.sendFeedback({ Text.translatable("info.redstone-helper.insta_on") }, false)
                    } else {
                        context.source.sendFeedback({ Text.translatable("info.redstone-helper.insta_off") }, false)
                    }
                    1
                }
            )
        )
    }
}