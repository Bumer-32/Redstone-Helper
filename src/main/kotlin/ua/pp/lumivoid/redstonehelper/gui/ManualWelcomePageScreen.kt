package ua.pp.lumivoid.redstonehelper.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.BoxComponent
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Color
import io.wispforest.owo.ui.core.Surface
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import ua.pp.lumivoid.redstonehelper.Config
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.util.Calculate

class ManualWelcomePageScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "manual_welcome_page"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        val layout = rootComponent.childById(FlowLayout::class.java, "layout")
        val checkVersionCommandBackgroundBox = rootComponent.childById(BoxComponent::class.java, "check_version_command_background_box")
        val checkVersionCommandBackgroundBoxOutline = rootComponent.childById(BoxComponent::class.java, "check_version_command_background_box_outline")
        val previousPageButton = rootComponent.childById(ButtonComponent::class.java, "previous_page_button")
        val nextPageButton = rootComponent.childById(ButtonComponent::class.java, "next_page_button")

        @Suppress("DuplicatedCode")
        if (Config().darkPanels) {
            layout.surface(Surface.DARK_PANEL)
        } else {
            layout.surface(Surface.PANEL)

            val color: Color
            Calculate.hexToRGB(0x3F3F3F).let { (r, g, b, a) ->
                color = Color(r, g, b, a)
            }
            layout.children().forEach { component ->
                if (component is LabelComponent) {
                    if (component.id()?.contains("__colored") != true) {
                        component.color(color)
                    }
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