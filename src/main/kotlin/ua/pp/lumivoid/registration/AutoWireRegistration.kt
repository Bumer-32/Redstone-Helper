package ua.pp.lumivoid.registration

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import ua.pp.lumivoid.events.PlayerBlockPlaceCallback

object AutoWireRegistration {
    fun register() {
        PlayerBlockPlaceCallback.EVENT.register(PlayerBlockPlaceCallback { player: PlayerEntity, world: World, blockState: BlockState, blockPos: BlockPos ->

            ActionResult.PASS
        })
    }
}