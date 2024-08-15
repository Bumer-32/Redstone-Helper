package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.sk89q.worldedit.IncompleteRegionException
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.fabric.FabricAdapter
import com.sk89q.worldedit.function.mask.Mask
import com.sk89q.worldedit.function.mask.Mask2D
import com.sk89q.worldedit.math.BlockVector3
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import ua.pp.lumivoid.Constants


object RstackCommand {
    private val logger = Constants.LOGGER

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        logger.debug("//rstack: Registering colorcode command")

        dispatcher.register(CommandManager.literal("/rstack")
            .requires {source -> source.hasPermissionLevel(2)}
            .executes { context ->
                logger.debug("//rstack: Missing arguments!")
                context.source.sendError(Text.translatable(Constants.LocalizeIds.STUFF_INFO_ERROR_MISSINGARGUMENTS))
                1
            }
            .then(CommandManager.argument("count", IntegerArgumentType.integer())
                .executes {
                    1
                }
            )
        )
    }

    fun execute(context: CommandContext<ServerCommandSource>) {
        logger.debug("//rstack: Executing colorcode command")

        try {
            val actor = FabricAdapter.adaptPlayer(context.source.player)
            val selection = WorldEdit.getInstance().sessionManager.get(actor).selection
            val selectionWorld = WorldEdit.getInstance().sessionManager.get(actor).selectionWorld

            val airFilter: Mask = object : Mask {
                override fun test(vector: BlockVector3): Boolean {
                    return "minecraft:air" != selectionWorld!!.getBlock(vector).blockType.id()
                }

                override fun toMask2D(): Mask2D? {
                    return null
                }
            }

            var playerFacing = actor.getLocation().directionEnum

        } catch (e: IncompleteRegionException) {
            context.source.sendError(Text.translatable(Constants.LocalizeIds.STUFF_INFO_ERROR_SELECTREGION))
        }
    }
}