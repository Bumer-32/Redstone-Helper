package ua.pp.lumivoid.redstonehelper.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.CheckboxComponent
import io.wispforest.owo.ui.component.DropdownComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Color
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.Surface
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.redstonehelper.ClientOptions
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.util.Calculate
import ua.pp.lumivoid.redstonehelper.util.features.AutoWire

class AutowireScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "autowire_ui_model"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building AutowireScreen UI")

        val layout = rootComponent.childById(FlowLayout::class.java, "layout")
        val autowireState = rootComponent.childById(CheckboxComponent::class.java, "autowireState")
        val selectMode = rootComponent.childById(ButtonComponent::class.java, "selectMode")
        val currentMode = rootComponent.childById(LabelComponent::class.java, "currentMode")

        @Suppress("DuplicatedCode")
        if (Config().darkPanels) {
            layout.surface(Surface.DARK_PANEL)
        } else {
            layout.surface(Surface.PANEL)

            val color: Color
            Calculate.hexToRGB(0x3F3F3F).let { (r, g, b, a) ->
                color = Color(r, g, b, a)
            }
            layout.children().forEach { component ->
                if (component is LabelComponent) {
                    if (component.id()?.contains("__colored") != true) {
                        component.color(color)
                    }
                }
            }
        }
        if (Config().enableBackgroundBlur) {
            rootComponent.surface(Surface.blur(100F, 10F))
        } else {
            rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
        }

        autowireState.checked(ClientOptions.isAutoWireEnabled)
        currentMode.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AUTOWIRE_CURRENTAUTOWIREMODE, Text.translatable("redstone-helper.feature.auto_wire.modes.${ClientOptions.autoWireMode.toString().lowercase()}")))

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
                    dropdown.button(Text.translatable("redstone-helper.feature.auto_wire.modes.${mode.toString().lowercase()}")) {
                        dropdown.remove()
                        AutoWire.setMode(AutoWire.valueOf(mode.toString()))
                        currentMode.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_AUTOWIRE_CURRENTAUTOWIREMODE, Text.translatable("redstone-helper.feature.auto_wire.modes.${ClientOptions.autoWireMode.toString().lowercase()}")))
                        logger.debug("Selected new auto wire mode: " + ClientOptions.autoWireMode)
                    }
                }

                val descriptions: MutableList<Component> = mutableListOf()
                dropdown.forEachDescendant { description ->
                    descriptions.add(description)
                }

                descriptions.remove(descriptions.first())
                descriptions.remove(descriptions.first())

                descriptions.zip(enumValues<AutoWire>().map { it.name }).forEach { (description, tooltip) ->
                    description.tooltip(Text.translatable("redstone-helper.feature.auto_wire.description.${tooltip.lowercase()}"))
                }
            }
        }
    }
}