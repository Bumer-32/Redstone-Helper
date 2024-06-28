package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.container.FlowLayout
import net.minecraft.util.Identifier

class BitsScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of("redstone-helper", "bits_ui_model"))) {
    override fun build(rootComponent: FlowLayout) {

    }
}