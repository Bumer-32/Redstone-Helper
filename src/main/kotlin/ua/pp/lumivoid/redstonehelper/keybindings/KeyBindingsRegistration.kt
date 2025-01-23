package ua.pp.lumivoid.redstonehelper.keybindings

import ua.pp.lumivoid.redstonehelper.keybindings.minecraft.UseKeyBinding

object KeyBindingsRegistration {
    fun register() {
        CalcKeyBinding.register()
        SwitchAutoWireKeyBinding.register()
        PreviousAutoWireModeKeyBinding.register()
        NextAutoWireModeKeyBinding.register()
        QuickTpKeyBinding.register()
        MacrosKeyBindings.register()

        // default minecraft
        UseKeyBinding.register()
    }
}