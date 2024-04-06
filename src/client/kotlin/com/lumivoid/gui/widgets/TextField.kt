package com.lumivoid.gui.widgets

import io.github.cottonmc.cotton.gui.widget.WTextField
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

class TextField(suggestion: Text): WTextField(suggestion) {
    private val logger = LoggerFactory.getLogger("redstone-helper")
    override fun renderSuggestion(context: DrawContext?, x: Int, y: Int) {
        super.renderSuggestion(context, x, y)
        this.requestFocus()
        logger.info("shown")
    }
}