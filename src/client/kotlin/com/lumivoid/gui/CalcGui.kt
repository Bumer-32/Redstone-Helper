package com.lumivoid.gui

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets


class CalcGui : LightweightGuiDescription() {
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(256, 20)
        root.setInsets(Insets.ROOT_PANEL)

//        val icon: WSprite = WSprite(Identifier("minecraft:textures/item/redstone.png"))
//        root.add(icon, 0, 2, 1, 1)

//        val button: WButton = WButton(Text.translatable("gui.examplemod.examplebutton"))
//        root.add(button, 0, 3, 4, 1)

//        val label = WLabel(Text.literal("Test"), 0xFFFFFF)
//        root.add(label, 0, 4, 2, 1)

        root.validate(this)
    }
}