package com.lumivoid.gui

import com.lumivoid.`fun`.Calculate
import com.lumivoid.gui.widgets.TextField
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WDynamicLabel
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.text.Text

class CalcGui : LightweightGuiDescription() {
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(256, 35)
        root.setInsets(Insets.ROOT_PANEL)

        val expressionField = TextField(Text.literal(""))
        expressionField.maxLength = 100
        expressionField.releaseFocus()
        root.add(expressionField, 0, 0, 14, 1)

        val resultText = WDynamicLabel{
            Calculate().calc(expressionField.text).toString()
        }
        resultText.setColor(0xFFFFFF, 0xFFFFFF)
        root.add(resultText, 0, 2, 2, 1)

        root.validate(this)
    }
}
