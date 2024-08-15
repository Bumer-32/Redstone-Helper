package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.CheckboxComponent
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.container.Containers
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.*
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.keybindings.MacrosKeyBindings
import ua.pp.lumivoid.util.Macro
import ua.pp.lumivoid.util.features.Macros

class MacroScreen(private val parent: Screen?): BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "macro_ui_model"))) {
    private val logger = Constants.LOGGER

    private var macrosLayout: FlowLayout? = null

    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building MacroScreen UI")

        val doneButton = rootComponent.childById(ButtonComponent::class.java, "done_button")
        val newMacroButton = rootComponent.childById(ButtonComponent::class.java, "new_macro_button")
        macrosLayout = rootComponent.childById(FlowLayout::class.java, "macros_panel")

        // icons
        // textures/gui/macros/cross.png
        // textures/gui/macros/edit.png

        macrosLayout!!.gap(1)

        Macros.listMacros().forEach { macro: Macro ->
            addMacro(macro.name, macro.enabled)
        }

        newMacroButton.onPress {
            this.client!!.setScreen(MacroEditScreen(this, "test", true))
        }

        doneButton.onPress {
            if (parent == null) {
                this.close()
            } else {
                this.client!!.setScreen(parent)
            }
        }
    }

    private fun addMacro(name: String, enabled: Boolean) {
        @Suppress("DuplicatedCode")
        macrosLayout!!.child(
            Containers.horizontalFlow(Sizing.fill(), Sizing.fixed(32))
                .child(
                    Containers.horizontalFlow(Sizing.fixed(250), Sizing.fill())
                        .child(
                            Containers.horizontalFlow(Sizing.content(), Sizing.content())
                                .child(
                                    Components.button(Text.literal("")) {
                                        Macros.removeMacro(name)
                                        update()
                                    }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                )
                                .child(
                                    Components.texture(Identifier.of(Constants.MOD_ID, "textures/gui/macros/cross.png"), 0, 0, 250, 250)
                                        .sizing(Sizing.fixed(20), Sizing.fixed(20))
                                        .positioning(Positioning.relative(0, 0))
                                )
                                .positioning(Positioning.relative(98, 50))
                        )
                        .child(
                            Containers.horizontalFlow(Sizing.content(), Sizing.content())
                                .child(
                                    Components.button(Text.literal("")) {
                                        this.client!!.setScreen(MacroEditScreen(this, name, false))
                                    }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                        .positioning(Positioning.relative(0, 0))
                                )
                                .child(
                                    Components.texture(Identifier.of(Constants.MOD_ID, "textures/gui/macros/pencil.png"), 0, 0, 250, 250)
                                        .sizing(Sizing.fixed(20), Sizing.fixed(20))
                                )
                                .positioning(Positioning.relative(88, 50))
                        )
                        .child(
                            Components.checkbox(Text.empty())
                                .positioning(Positioning.relative(2, 60))
                                .tooltip(Text.translatable(Constants.LocalizeIds.FEATURE_MACRO_ENABLEDFORKEYBINDS))
                                .configure {
                                    val checkBox = it as CheckboxComponent
                                    checkBox.checked(enabled)
                                    checkBox.onChanged { checked ->
                                        logger.debug("Macro $name enabled: $checked")
                                        val macro = Macros.readMacro(name)
                                        macro!!.enabled = checked
                                        Macros.editMacro(name, macro)
                                        MacrosKeyBindings.updateMacros()
                                        update()
                                    }
                                }
                        )
                        .child(
                            Components.label(Text.literal(name))
                                .positioning(Positioning.relative(15, 50))
                        )
                        .surface(Surface.flat(0xFF0F0F0F.toInt()))
                        .verticalAlignment(VerticalAlignment.CENTER)
                )
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .id(name)

        )
    }

    fun update() {
        macrosLayout!!.clearChildren()
        Macros.listMacros().forEach { macro: Macro ->
            addMacro(macro.name, macro.enabled)
        }
    }
}

