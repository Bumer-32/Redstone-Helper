/**
 * Code by: https://github.com/RedstoneTools/redstonetools-mod/blob/main/src/main/java/tools/redstone/redstonetools/mixin/gamerules/DoContainerDropsMixin.java
 * License in: REDSTONE_TOOLS_LICENSE
 */

package ua.pp.lumivoid.redstonehelper.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ua.pp.lumivoid.redstonehelper.registration.GamerulesRegistration;

@Mixin(ItemScatterer.class)
public class DoContainerDropsMixin {
    @Inject(method = "spawn(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/inventory/Inventory;)V", at = @At("HEAD"), cancellable = true)
    private static void spawn(World world, BlockPos pos, Inventory inventory, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(GamerulesRegistration.INSTANCE.getDO_CONTAINER_DROPS())) ci.cancel();
    }
}
