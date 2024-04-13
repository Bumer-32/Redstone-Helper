package com.lumivoid.gui.widgets

import io.github.cottonmc.cotton.gui.widget.WTextField
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

class TextField(suggestion: Text): WTextField(suggestion) {
    override fun renderSuggestion(context: DrawContext?, x: Int, y: Int) {
        this.requestFocus()
        super.renderSuggestion(context, x, y)
    }
}