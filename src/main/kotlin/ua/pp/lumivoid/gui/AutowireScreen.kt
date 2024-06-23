package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.CheckboxComponent
import io.wispforest.owo.ui.component.DropdownComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Component
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.ClientOptions
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.AutoWire

class AutowireScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of("redstone-helper", "autowire_ui_model"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        val autowireState = rootComponent.childById(CheckboxComponent::class.java, "autowireState")
        val selectMode = rootComponent.childById(ButtonComponent::class.java, "selectMode")
        val currentMode = rootComponent.childById(LabelComponent::class.java, "currentMode")
        val selectBlock = rootComponent.childById(TextFieldWidget::class.java, "selectBlock")

        autowireState.checked(ClientOptions.isAutoWireEnabled)

        println(autowireState.message)

        currentMode.text(Text.translatable("gui.redstone-helper.current_mode", Text.translatable("gui.redstone-helper.${ClientOptions.autoWireMode.toString().lowercase()}")))

        selectBlock.setEditableColor(0x00FF00)

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
                        currentMode.text(Text.translatable("gui.redstone-helper.current_mode", Text.translatable("gui.redstone-helper.${ClientOptions.autoWireMode.toString().lowercase()}")))
                        logger.debug("selected new auto wire mode: " + ClientOptions.autoWireMode)
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

        selectBlock.setChangedListener {
            if (Registries.BLOCK.containsId(Identifier.of(selectBlock.text))) {
                ClientOptions.autoWireBlock = selectBlock.text
                selectBlock.setEditableColor(0x00FF00)
                logger.debug("selected new auto wire block: " + ClientOptions.autoWireBlock)
            } else {
                ClientOptions.autoWireBlock = "minecraft:smooth_stone"
                selectBlock.setEditableColor(0xFF0000)
                logger.debug("wrong auto wire block selected, reject, new block is: " + ClientOptions.autoWireBlock)
            }
        }
    }
}