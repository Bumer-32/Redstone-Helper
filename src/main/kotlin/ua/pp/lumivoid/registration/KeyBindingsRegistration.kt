package ua.pp.lumivoid.registration

import ua.pp.lumivoid.keybindings.*
import ua.pp.lumivoid.keybindings.minecraft.UseKeyBinding

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