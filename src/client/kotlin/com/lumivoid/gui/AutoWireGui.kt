package com.lumivoid.gui

import com.lumivoid.Constants
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WButton
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

class AutoWireGui: LightweightGuiDescription() {
    private val logger = LoggerFactory.getLogger(Constants.MOD_ID)
    init {
        val root = WGridPanel() // TODO: other panel
        setRootPanel(root)
        root.setSize(90, 90)
        root.setInsets(Insets.ROOT_PANEL)

        val selectAutoWireMode = WButton(Text.translatable("gui.redstone-helper.select_auto_wire_mode"))
        root.add(selectAutoWireMode, 0, 0, 4, 1) // TODO: add list with modes

        root.validate(this)
    }
}