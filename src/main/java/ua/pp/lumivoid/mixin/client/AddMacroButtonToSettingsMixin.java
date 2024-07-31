package ua.pp.lumivoid.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ua.pp.lumivoid.Constants;
import ua.pp.lumivoid.gui.MacroScreen;

import java.util.List;
import java.util.Objects;

@Mixin(ControlsOptionsScreen.class)
public abstract class AddMacroButtonToSettingsMixin extends GameOptionsScreen {
	protected AddMacroButtonToSettingsMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Inject(at = @At("TAIL"), method = "addOptions()V")
	private void addMacroButton(CallbackInfo ci) {
		Objects.requireNonNull(this.body).addAll(List.of(ButtonWidget.builder(Text.translatable(Constants.LocalizeIds.FEATURE_MACRO_SETTINGSMACROSBUTTON), buttonWidget -> Objects.requireNonNull(this.client).setScreen(new MacroScreen(this))).build()));
	}
}