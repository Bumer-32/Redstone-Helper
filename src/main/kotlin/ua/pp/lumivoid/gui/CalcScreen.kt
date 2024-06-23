package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.util.Calculate

class CalcScreen : BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of("redstone-helper", "calc_ui_model"))) {
    override fun build(rootComponent: FlowLayout) {
        val expressionField = rootComponent.childById(TextFieldWidget::class.java, "expression")
        val result = rootComponent.childById(LabelComponent::class.java, "result")
        result.text(Text.translatable("gui.redstone-helper.invalid_expression"))
        expressionField.setChangedListener {
            val calcResult = Calculate.calc(expressionField.text)
            if (calcResult.isNaN()) {
                result.text(Text.translatable("gui.redstone-helper.invalid_expression"))
            } else {
                result.text(Text.translatable("gui.redstone-helper.result", calcResult))
            }
        }
    }
}

