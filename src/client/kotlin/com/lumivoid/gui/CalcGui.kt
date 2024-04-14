package com.lumivoid.gui

import com.lumivoid.util.Calculate
import com.lumivoid.gui.widgets.TextField
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.text.Text

class CalcGui : LightweightGuiDescription() {
    init {
        val root = WGridPanel() // TODO: other panel
        setRootPanel(root)
        root.setSize(256, 40)
        root.setInsets(Insets.ROOT_PANEL)

        val name = WLabel(Text.translatable("gui.redstone-helper.calculator"))
        name.setHorizontalAlignment(HorizontalAlignment.CENTER)
        root.add(name, 0, 0)

        val expressionField = TextField(Text.translatable("gui.redstone-helper.expression"))
        expressionField.maxLength = 100
        expressionField.releaseFocus()
        root.add(expressionField, 0, 1, 14, 1)

        val resultText = WLabel(Calculate.calc(expressionField.text))
        resultText.setColor(0xFFFFFF, 0xFFFFFF)
        expressionField.setChangedListener {
            resultText.text = Calculate.calc(expressionField.text)
        }
        root.add(resultText, 0, 3, 2, 1)

        root.validate(this)
    }
}
