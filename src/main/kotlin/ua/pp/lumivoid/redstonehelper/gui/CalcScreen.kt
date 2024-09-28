package ua.pp.lumivoid.redstonehelper.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Color
import io.wispforest.owo.ui.core.Surface
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.util.Calculate

class CalcScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "calc_ui_model"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building CalcScreen UI")

        val layout = rootComponent.childById(FlowLayout::class.java, "layout")
        val expressionField = rootComponent.childById(TextFieldWidget::class.java, "expression")
        val result = rootComponent.childById(LabelComponent::class.java, "result")

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

        result.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_CALCULATOR_INVALIDEXPRESSION))
        expressionField.setMaxLength(999999999)

        expressionField.setChangedListener {
            logger.debug("Calculating new expression: ${expressionField.text}")

            val calcResult = Calculate.calc(expressionField.text)
            if (calcResult.isNaN()) {
                result.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_CALCULATOR_INVALIDEXPRESSION))
            } else {
                result.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_CALCULATOR_RESULT, calcResult))
            }
        }
    }
}

