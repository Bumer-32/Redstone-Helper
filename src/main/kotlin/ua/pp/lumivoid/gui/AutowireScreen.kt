package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.CheckboxComponent
import io.wispforest.owo.ui.component.DropdownComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.Surface
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.features.AutoWire

class AutowireScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "autowire_ui_model"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building AutowireScreen UI")

        val layout = rootComponent.childById(FlowLayout::class.java, "layout")
        val autowireState = rootComponent.childById(CheckboxComponent::class.java, "autowireState")
        val selectMode = rootComponent.childById(ButtonComponent::class.java, "selectMode")
        val currentMode = rootComponent.childById(LabelComponent::class.java, "currentMode")

        if (Config().darkPanels) {
            layout.surface(Surface.DARK_PANEL)
        } else {
            layout.surface(Surface.PANEL)
        }
        if (Config().enableBackgroundBlur) {
            rootComponent.surface(Surface.blur(100F, 10F))
        } else {
            rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
        }

        autowireState.checked(ClientOptions.isAutoWireEnabled)
        currentMode.text(Text.translatable("gui.redstone-helper.current_auto_wire_mode", Text.translatable("gui.redstone-helper.${ClientOptions.autoWireMode.toString().lowercase()}")))

        autowireState.onChanged {
            ClientOptions.isAutoWireEnabled = autowireState.isChecked
        }

        selectMode.onPress {
            DropdownComponent.openContextMenu(
                this,
                rootComponent,
                FlowLayout::child,
                selectMode.x.toDouble(),
                selectMode.y.toDouble() + selectMode.height.toDouble(),
            ) { dropdown ->
                dropdown.allowOverflow(true)
                dropdown.zIndex(150)

                for (mode in AutoWire.entries) {
                    dropdown.button(Text.translatable("gui.redstone-helper.${mode.toString().lowercase()}")) {
                        dropdown.remove()
                        ClientOptions.autoWireMode = AutoWire.valueOf(mode.toString())
                        currentMode.text(Text.translatable("gui.redstone-helper.current_auto_wire_mode", Text.translatable("gui.redstone-helper.${ClientOptions.autoWireMode.toString().lowercase()}")))
                        logger.debug("Selected new auto wire mode: " + ClientOptions.autoWireMode)
                    }
                }

                val descends: MutableList<Component> = mutableListOf()
                dropdown.forEachDescendant { descendant ->
                    descends.add(descendant)
                }

                descends.remove(descends.first())
                descends.remove(descends.first())

                descends.zip(enumValues<AutoWire>().map { it.name }).forEach { (descendant, tooltip) ->
                    descendant.tooltip(Text.translatable("gui.redstone-helper.${tooltip.lowercase()}_desc"))
                }
            }
        }
    }
}