package ua.pp.lumivoid.registration

import ua.pp.lumivoid.keybindings.*

object KeyBindingsRegistration {
    fun register() {
        CalcKeyBinding.register()
        SwitchAutoWireKeyBinding.register()
        PreviousAutoWireModeKeyBinding.register()
        NextAutoWireModeKeyBinding.register()
        QuickTpKeyBinding.register()
    }
}