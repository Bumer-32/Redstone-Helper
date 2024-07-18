@file:Suppress("LoggingSimilarMessage")

package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.gui.HudToast
import ua.pp.lumivoid.gui.ManualWelcomePageScreen
import ua.pp.lumivoid.util.DownloadManager
import ua.pp.lumivoid.util.VersionChecker

object RedstoneHelperCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/redstone-helper: Registering redstone-helper command")

        dispatcher.register(ClientCommandManager.literal("redstone-helper")
            .executes { context ->
                logger.debug("/redstone-helper: Missing arguments!")
                context.source.sendError(Text.translatable("info_error.redstone-helper.missing_arguments"))
                1
            }
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
            .then(ClientCommandManager.literal("check-updates")
                .executes { context ->
                    context.source.sendFeedback(
                        VersionChecker.checkRedstoneHelperVersionLocalized(
                            checkForRealease = true,
                            checkForBeta = true,
                            checkForAlpha = true
                        )
                    )
                    1
                }
                .then(ClientCommandManager.literal("by-config")
                    .executes { context ->
                        val config = Config()
                        context.source.sendFeedback(
                            VersionChecker.checkRedstoneHelperVersionLocalized(
                                config.checkForRelease,
                                config.checkForBeta,
                                config.checkForAlpha
                            )
                        )
                        1
                    }
                )
            )
            .then(ClientCommandManager.literal("notification")
                .executes { context ->
                    logger.debug("/redstone-helper: Missing arguments!")
                    context.source.sendError(Text.translatable("info_error.redstone-helper.missing_arguments"))
                    1
                }
                .then(ClientCommandManager.literal("clear-queue")
                    .executes {
                        HudToast.clearQueue()
                        1
                    }
                )
            )
            .then(ClientCommandManager.literal("test")
                .then(ClientCommandManager.literal("notification")
                    .then(ClientCommandManager.argument("count", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("short", BoolArgumentType.bool())
                            .executes { context ->
                                context.source.sendFeedback(Text.literal("Testing notification"))
                                for (i in 1..IntegerArgumentType.getInteger(context, "count")) {
                                    HudToast.addToastToQueue(Text.literal(" Test notification $i\n Hello World!\nYour ad here"), BoolArgumentType.getBool(context, "short"))
                                }
                                1
                            }
                        )
                    )
                )
                .then(ClientCommandManager.literal("raw-notification")
                    .then(ClientCommandManager.argument("short", BoolArgumentType.bool())
                        .executes { context ->
                            context.source.sendFeedback(Text.literal("Testing raw notification"))
                            HudToast.showToast(Text.literal("Test raw notification"), BoolArgumentType.getBool(context, "short"))
                            1
                        }
                    )
                )
                .then(ClientCommandManager.literal("help")
                    .executes {
                        logger.debug("/redstone-helper: Opening manual menu")
                        MinecraftClient.getInstance().send(Runnable {
                            MinecraftClient.getInstance().setScreen(ManualWelcomePageScreen())
                        })
                        1
                    }
                )
            )
            .then(ClientCommandManager.literal("cleanUp")
                .executes {
                    logger.debug("/redstone-helper: Cleaning up")
                    DownloadManager.cleanUp()
                    1
                }
            )
        )
    }
}