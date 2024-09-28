@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.redstonehelper.gui

import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Easing
import io.wispforest.owo.ui.core.Positioning
import io.wispforest.owo.ui.hud.Hud
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.client.MinecraftClient
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants


object HudToast {
    private val logger = Constants.LOGGER

    private val queue = mutableListOf<() -> Unit>()
    private var isToastActive = false

    private val component = Hud.getComponent(Constants.TOAST_ID)!! as FlowLayout

    private fun showToast(text: Text, short: Boolean) {
        logger.debug("Showing toast")

        isToastActive = true

        val scope = CoroutineScope(Dispatchers.Default)
        val config = Config()

        component.childById(LabelComponent::class.java, "text")?.text(text)

        scope.launch {
            val player = MinecraftClient.getInstance().player

            player!!.playSound(Registries.SOUND_EVENT.get(Identifier.of("minecraft", "ui.toast.in")))

            component.positioning().animate(500, Easing.LINEAR, Positioning.relative(config.toastPosition.xPos(), config.toastPosition.yPos())).forwards()

            if (short) {
                delay(1500)
            } else {
                delay(5000)
            }

            player.playSound(Registries.SOUND_EVENT.get(Identifier.of("minecraft", "ui.toast.out")))

            component.positioning().animate(500, Easing.LINEAR, Positioning.relative(config.toastPosition.hidedXPos(), config.toastPosition.yPos())).forwards()

            if (short) {
                delay(500)
            } else {
                delay(1000)
            }

            component.childById(LabelComponent::class.java, "text")?.text(Text.empty())

            if (queue.isNotEmpty()) {
                updateQueue()
            } else {
                isToastActive = false
            }
        }
    }

    fun addToastToQueue(text: Text, short: Boolean = false) {
        queue.add { showToast(text, short) }
        if (!isToastActive) {
            updateQueue()
        }
    }

    fun clearQueue() {
        queue.clear()
    }

    private fun updateQueue() {
        val iterator = queue.iterator()
        if (iterator.hasNext()) {
            val toast = iterator.next()
            toast()
            iterator.remove()
        }
    }
}