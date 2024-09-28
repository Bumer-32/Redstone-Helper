@file:Suppress("LoggingSimilarMessage")

package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.gui.HudToast
import ua.pp.lumivoid.redstonehelper.util.DownloadManager
import ua.pp.lumivoid.redstonehelper.util.VersionChecker

object RedstoneHelperCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource?>) {
        logger.debug("/redstone-helper: Registering redstone-helper command")

        dispatcher.register(ClientCommandManager.literal("redstone-helper")
            .executes { context ->
                logger.debug("/redstone-helper: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(ClientCommandManager.literal("check-updates")
                .executes { context ->
                    val checkerText = VersionChecker.checkRedstoneHelperVersionLocalized(
                        checkForRelease = true,
                        checkForBeta = true,
                        checkForAlpha = true
                    )
                    context.source.sendFeedback(checkerText)
                    if (checkerText != Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_UPTODATE) && checkerText != Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_UNABLETOCHECKVERSION)) {
                        context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_SKIPVERSION))
                    }
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
                .then(ClientCommandManager.literal("skip")
                    .executes { context ->
                        context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_SKIPPING))
                        VersionChecker.skipVersion()
                        VersionChecker.checkRedstoneHelperVersionWithToast()
                        1
                    }
                )
                .then(ClientCommandManager.literal("clear-skips")
                    .executes { context ->
                        context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.STUFF_VERSIONCHECKER_CLEARSKIPS))
                        VersionChecker.clearSkippedVersion()
                        VersionChecker.checkRedstoneHelperVersionWithToast()
                        1
                    }
                )
            )
            .then(ClientCommandManager.literal("notification")
                .executes { context ->
                    logger.debug("/redstone-helper: Missing arguments!")
                    context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                    1
                }
                .then(ClientCommandManager.literal("clear-queue")
                    .executes {
                        HudToast.clearQueue()
                        1
                    }
                )
                .then(ClientCommandManager.literal("add-to-queue")
                    .executes { context ->
                        logger.debug("/redstone-helper: Missing arguments!")
                        context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                        1
                    }
                    .then(ClientCommandManager.argument("short", BoolArgumentType.bool())
                        .executes { context ->
                            logger.debug("/redstone-helper: Missing arguments!")
                            context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                            1
                        }
                        .then(ClientCommandManager.argument("text", StringArgumentType.greedyString())
                            .executes { context ->
                                HudToast.addToastToQueue(Text.literal(StringArgumentType.getString(context, "text")), BoolArgumentType.getBool(context, "short"))
                                1
                            }
                        )
                    )
                )
            )
            .then(ClientCommandManager.literal("test")
                .executes {
                    //for (i in 1..Constants.LOCALIZEIDS.Counts.HINTS_COUNT) HudToast.addToastToQueue(Text.translatable("redstone-helper.feature.hints.hint.$i"), false)
                    1
                }
            )
            .then(ClientCommandManager.literal("cleanUp")
                .executes {
                    logger.debug("/redstone-helper: Cleaning up")
                    DownloadManager.cleanUp()
                    1
                }
            )
            .then(ClientCommandManager.literal("github")
                .executes { context ->
                    context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.STUFF_GITHUB))
                    1
                }
            )
            .then(ClientCommandManager.literal("crowdin")
                .executes { context ->
                    context.source.sendFeedback(Text.translatable(Constants.LOCALIZEIDS.STUFF_CROWDIN))
                    1
                }
            )
        )
    }
}