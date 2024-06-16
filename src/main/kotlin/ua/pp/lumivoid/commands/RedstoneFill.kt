package ua.pp.lumivoid.commands

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.client.MinecraftClient
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.hit.HitResult.Type
import ua.pp.lumivoid.util.SendPacket


object RedstoneFill {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>) {
        dispatcher.register(
            CommandManager.literal("redstone-fill")
            .executes {
                val client: MinecraftClient = MinecraftClient.getInstance()
                val hit: HitResult = client.crosshairTarget!!

                if (hit.type == Type.BLOCK) {
                    val blockHit = hit as BlockHitResult
                    val blockPos = blockHit.blockPos
                    val blockEntity = client.player!!.world.getBlockEntity(blockPos) // TODO find server world and make autofill

                    SendPacket.clientGetItemPacket(blockPos, 2)
                }

                1
            }
        )
    }
}