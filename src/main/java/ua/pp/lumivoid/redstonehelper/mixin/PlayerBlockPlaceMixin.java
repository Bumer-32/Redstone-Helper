package ua.pp.lumivoid.redstonehelper.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ua.pp.lumivoid.redstonehelper.events.PlayerBlockPlaceCallback;

import java.util.Objects;

@Mixin(BlockItem.class)
public class PlayerBlockPlaceMixin {
    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"))
    private void place(ItemPlacementContext context, BlockState state, CallbackInfoReturnable<Boolean> info) {
        ActionResult result = PlayerBlockPlaceCallback.EVENT.invoker().interact(
                Objects.requireNonNull(context.getPlayer()),
                context.getWorld(),
                state,
                context.getBlockPos()
        );

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
