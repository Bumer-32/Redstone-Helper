package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.BoxComponent
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Color
import io.wispforest.owo.ui.core.Surface
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.Calculate

class ManualWelcomePageScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "manual_welcome_page"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        val layout = rootComponent.childById(FlowLayout::class.java, "layout")
        val manualLayout = rootComponent.childById(FlowLayout::class.java, "manual_layout")
        val checkVersionCommandBackgroundBox = rootComponent.childById(BoxComponent::class.java, "check_version_command_background_box")
        val checkVersionCommandBackgroundBoxOutline = rootComponent.childById(BoxComponent::class.java, "check_version_command_background_box_outline")
        val previousPageButton = rootComponent.childById(ButtonComponent::class.java, "previous_page_button")
        val nextPageButton = rootComponent.childById(ButtonComponent::class.java, "next_page_button")

        if (Config().darkPanels) {
            layout.surface(Surface.DARK_PANEL)
        } else {
            layout.surface(Surface.PANEL)

            val color: Color
            Calculate.hexToRGB(0x3F3F3F).let { (r, g, b, a) ->
                color = Color(r, g, b, a)
            }
            layout.children().forEach { component ->
                logger.info("child")
                if (component is LabelComponent && !component.text().string.contains("__colored")) {
                    logger.info(component.text().string)
                    component.color(color)
                }
            }
        }
        if (Config().enableBackgroundBlur) {
            rootComponent.surface(Surface.blur(100F, 10F))
        } else {
            rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
        }

        Calculate.hexToRGB(0x77000000).let {(r, g, b, a) ->
            checkVersionCommandBackgroundBox.color(Color(r, g, b, a))
        }
        Calculate.hexToRGB((0xFF121212).toInt()).let {(r, g, b, a) ->
            checkVersionCommandBackgroundBoxOutline.color(Color(r, g, b, a))
        }

        previousPageButton.onPress {
            logger.info("Inspector")
            this.uiAdapter.toggleInspector()
        }
        nextPageButton.onPress {
            logger.info("Update")
            MinecraftClient.getInstance().setScreen(ManualWelcomePageScreen())
        }
    }
}