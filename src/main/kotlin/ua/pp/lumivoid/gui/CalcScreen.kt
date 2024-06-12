package ua.pp.lumivoid.gui

import ua.pp.lumivoid.util.Calculate
import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.util.Identifier

class CalcScreen : BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier("redstone-helper", "calc_ui_model"))) {
    override fun build(rootComponent: FlowLayout) {
        val expressionField = rootComponent.childById(TextFieldWidget::class.java, "expression")
        val result = rootComponent.childById(LabelComponent::class.java, "result")
        result.text(Calculate.calc(expressionField.text))
        expressionField.setChangedListener {
            result.text(Calculate.calc(expressionField.text))
        }
    }
}

