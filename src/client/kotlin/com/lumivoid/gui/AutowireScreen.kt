package com.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.container.FlowLayout
import net.minecraft.util.Identifier

class AutowireScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier("redstone-helper", "autowire_ui_model"))) {
    override fun build(rootComponent: FlowLayout?) {
        //TODO("Not yet implemented")
    }
}