package ua.pp.lumivoid.redstonehelpertestmod.mixin.client;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ua.pp.lumivoid.redstonehelpertestmod.Constants;
import ua.pp.lumivoid.redstonehelpertestmod.RedstoneHelperTestMod;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Unique
    private static Boolean launched = false;

    @Inject(at = @At("TAIL"), method = "init()V")
    private void init(CallbackInfo ci) {
        if(!launched) {
            launched = true;
            Constants.INSTANCE.getLOGGER().info("Good news! Minecraft started with Redstone Helper!");
            RedstoneHelperTestMod.INSTANCE.endOfTest();
        }
    }
}
