@file:Suppress("LoggingStringTemplateAsArgument")

package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.core.Positioning
import io.wispforest.owo.ui.hud.Hud
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.client.MinecraftClient
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.Constants
import kotlin.concurrent.fixedRateTimer
import kotlin.math.roundToInt


object HudToast {
    private val logger = Constants.LOGGER

    private val scope = CoroutineScope(Dispatchers.Default)
    private val queue = mutableListOf<() -> Unit>()
    private var isToastActive = false

    private fun showToast(text: Text) {
        logger.debug("Showing toast")

        isToastActive = true

        val component = Hud.getComponent(Constants.TOAST_ID)

        component!!.root().childById(LabelComponent::class.java, "text")?.text(text)

        // TODO add keybind for autowire

        scope.launch {
            val player = MinecraftClient.getInstance().player

            player!!.playSound(Registries.SOUND_EVENT.get(Identifier.of("minecraft", "ui.toast.in")))
            animateValue(200, 100, 500 /* in millis */ ) { value ->
                component.positioning(Positioning.relative(value, 35))
            }

            Thread.sleep(5000)

            player.playSound(Registries.SOUND_EVENT.get(Identifier.of("minecraft", "ui.toast.out")))
            animateValue(100, 200, 500 /* in millis */ ) { value ->
                component.positioning(Positioning.relative(value, 35))
            }
            Thread.sleep(2500)

            if (queue.isNotEmpty()) {
                updateQueue()
            } else {
                isToastActive = false
            }
        }
    }

    fun addToastToQueue(text: Text) {
        queue.add { showToast(text) }
        if (!isToastActive) {
            updateQueue()
        }
    }

    private fun updateQueue() {
        val iterator = queue.iterator()
        if (iterator.hasNext()) {
            println(queue.size)
            val toast = iterator.next()
            toast()
            iterator.remove()
        }
    }

    @Suppress("SameParameterValue")
    private fun animateValue(from: Int, to: Int, duration: Long, onUpdate: (Int) -> Unit) {
        val startTime = System.currentTimeMillis()
        fixedRateTimer(period = 16) {  // Near 60 FPS
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - startTime
            val fraction = (elapsedTime / duration.toFloat()).coerceAtMost(1.0f)

            val currentValue = (from + (to - from) * fraction).roundToInt()
            onUpdate(currentValue)

            if (fraction >= 1.0f) {
                this.cancel()
            }
        }
    }
}