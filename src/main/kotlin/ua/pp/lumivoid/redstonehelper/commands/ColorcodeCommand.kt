package ua.pp.lumivoid.redstonehelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.sk89q.worldedit.IncompleteRegionException
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.fabric.FabricAdapter
import net.minecraft.registry.Registries
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.commands.commandsuggestionproviders.ColorCommandSuggestionProvider
import ua.pp.lumivoid.redstonehelper.network.SendPacket
import ua.pp.lumivoid.redstonehelper.network.packets.c2s.SetBlockC2SPacket


object ColorcodeCommand {
    private val logger = Constants.LOGGER

    private val coloredBlocksList = listOf(
        "wool",
        "carpet",
        "terracotta",
        "concrete",
        "concrete_powder",
        "glazed_terracotta",
        "stained_glass",
        "stained_glass_pane",
        "shulker_box",
        "bed",
        "candle",
    )

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        logger.debug("//colorcode: Registering colorcode command")

        dispatcher.register(CommandManager.literal("/colorcode")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context ->
                logger.debug("//colorcode: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(CommandManager.argument("color", StringArgumentType.word())
                .suggests(ColorCommandSuggestionProvider)
                .executes { context ->
                    logger.debug("//colorcode: Executing colorcode command")

                    try {
                        val selection = WorldEdit.getInstance().sessionManager.get(FabricAdapter.adaptPlayer(context.source.player)).selection

                        var totalBlocksColored = 0

                        selection.forEach { blockVector3 ->
                            val blockPos = BlockPos(blockVector3.x(), blockVector3.y(), blockVector3.z())
                            val block = context.source.world.getBlockState(blockPos).block
                            val blockId = Registries.BLOCK.getId(block)

                            coloredBlocksList.any {
                                if (blockId.toString().contains(it)) {
                                    val fullId = Identifier.of(
                                        blockId.namespace,
                                        "${StringArgumentType.getString(context, "color")}_${it}"
                                    )
                                    if (Registries.BLOCK.containsId(fullId)) {
                                        SendPacket.sendPacket(SetBlockC2SPacket(blockPos, fullId, Direction.UP,  false, Constants.aMinecraftClass))
                                        totalBlocksColored++
                                    }
                                    return@any true
                                }
                                false
                            }
                        }
                        context.source.sendFeedback({ Text.translatable(Constants.LOCALIZEIDS.MOD_STYLEDSHORTREDSTONEHELPERTITLE).append(Constants.TEXT_SPACE).append(Text.translatable(Constants.LOCALIZEIDS.FEATURE_COLORCODE_SUCCESS, totalBlocksColored, StringArgumentType.getString(context, "color"))) }, false)
                    } catch (e: IncompleteRegionException) {
                        context.source.sendError(Text.translatable(Constants.LOCALIZEIDS.STUFF_INFO_ERROR_SELECTREGION))
                    }
                    1
                }
            )
        )
    }
}