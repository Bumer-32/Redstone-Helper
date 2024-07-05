package ua.pp.lumivoid.registration

import ua.pp.lumivoid.keybindings.AutoWireKeyBinding
import ua.pp.lumivoid.keybindings.CalcKeyBinding
import ua.pp.lumivoid.keybindings.QuickTpKeyBinding

object KeyBindingsRegistration {
    fun register() {
        CalcKeyBinding.register()
        AutoWireKeyBinding.register()
        QuickTpKeyBinding.register()
    }
}