package com.lumivoid.gui.widgets

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.data.InputResult
import net.minecraft.client.gui.DrawContext

class ComboBox: WWidget() {
    // Uncompleted widget, maybe for future

    //0xAARRGGBB A - Alpha R - Red G - Green B - Blue
    private val BACKGROUND_COLOR: Int = 0xFF000000 .toInt()
    private val ACTIVE_BORDER_COLOR: Int = 0xFFFFFFA0 . toInt()
    private val NONACTIVE_BORDER_COLOR: Int = 0xFF3B3B04 .toInt()

    private var isActive: Boolean = true
    private var isOpen: Boolean = false

    private var onSelected: ((String) -> Unit)? = null

    override fun canResize(): Boolean {
        return true
    }

    private fun renderComboBox(context: DrawContext, x: Int, y: Int) {
        val borderColor: Int = if (isActive) ACTIVE_BORDER_COLOR else NONACTIVE_BORDER_COLOR
        ScreenDrawing.coloredRect(context, x - 1, y - 1, width + 2, height + 2, borderColor)
        ScreenDrawing.coloredRect(context, x, y, width, height, BACKGROUND_COLOR)

        if (isOpen) {
            ScreenDrawing.coloredRect(context, x - 1, y + height, width + 2, height + 2, ACTIVE_BORDER_COLOR)
            ScreenDrawing.coloredRect(context, x, y + height + 1, width, height, BACKGROUND_COLOR)
        }
    }



    override fun paint(context: DrawContext, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        renderComboBox(context, x, y)
    }

    override fun onClick(x: Int, y: Int, button: Int): InputResult {
        if (isOpen) {
            closeMenu()
        } else {
            openMenu()
        }
        return super.onClick(x, y, button)
    }

    fun openMenu() {
        isOpen = true
    }

    fun closeMenu() {
        isOpen = false
    }

    fun setActive(isActive: Boolean) {
        this.isActive = isActive
    }

    fun getActive(): Boolean {
        return isActive
    }

    fun setOnSelectedListener(onSelected: ((String) -> Unit)) {
        this.onSelected = onSelected
    } //onSelected?.invoke("selected hz") // to invoke this shit
}