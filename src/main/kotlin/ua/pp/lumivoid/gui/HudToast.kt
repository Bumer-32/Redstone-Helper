@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.core.Easing
import io.wispforest.owo.ui.core.Positioning
import io.wispforest.owo.ui.hud.Hud
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.client.MinecraftClient
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants


object HudToast {
    private val logger = Constants.LOGGER

    private val queue = mutableListOf<() -> Unit>()
    private var isToastActive = false

    private val component = Hud.getComponent(Constants.TOAST_ID)

    fun showToast(text: Text, short: Boolean) {
        logger.debug("Showing toast")

        isToastActive = true

        val scope = CoroutineScope(Dispatchers.Default)
        val config = Config()

        component!!.root().childById(LabelComponent::class.java, "text")?.text(text)

        scope.launch {
            val player = MinecraftClient.getInstance().player

            player!!.playSound(Registries.SOUND_EVENT.get(Identifier.of("minecraft", "ui.toast.in")))

            component.positioning().animate(500, Easing.LINEAR, Positioning.relative(config.toastPosition.xPos(), config.toastPosition.yPos())).forwards()

            if (short) {
                Thread.sleep(1500)
            } else {
                Thread.sleep(5000)
            }

            player.playSound(Registries.SOUND_EVENT.get(Identifier.of("minecraft", "ui.toast.out")))

            component.positioning().animate(500, Easing.LINEAR, Positioning.relative(config.toastPosition.hidedXPos(), config.toastPosition.yPos())).forwards()

            if (short) {
                Thread.sleep(500)
            } else {
                Thread.sleep(1000)
            }

            component.root().childById(LabelComponent::class.java, "text")?.text(Text.empty())

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