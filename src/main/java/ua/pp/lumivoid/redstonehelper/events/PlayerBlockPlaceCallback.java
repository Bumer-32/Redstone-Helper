package ua.pp.lumivoid.redstonehelper.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PlayerBlockPlaceCallback {
    //idk how to do this with kotlin then we can use java with official fabric wiki

    Event<PlayerBlockPlaceCallback> EVENT = EventFactory.createArrayBacked(PlayerBlockPlaceCallback.class,
            (listeners) -> (player, world, blockState, blockPos) -> {
                for (PlayerBlockPlaceCallback listener : listeners) {
                    ActionResult result = listener.interact(player, world, blockState, blockPos);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, World world, BlockState blockState, BlockPos blockPos);
}