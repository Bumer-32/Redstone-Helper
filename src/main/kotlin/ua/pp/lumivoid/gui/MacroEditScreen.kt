package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.component.TextureComponent
import io.wispforest.owo.ui.container.Containers
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.keybindings.MacrosKeyBindings
import ua.pp.lumivoid.util.Macro
import ua.pp.lumivoid.util.features.Macros


class MacroEditScreen(private val parent: MacroScreen?, name:String, private val new: Boolean): BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "macro_edit_ui_model"))) {
    private val logger = Constants.LOGGER
    
    private var commandsLayout: FlowLayout? = null
    private var macro: Macro? = null

    init {
        macro = if (!new) {
            Macros.readMacro(name)
        } else {
            Macro(name, GLFW.GLFW_KEY_UNKNOWN, false, mutableListOf())
        }
    }

    @Suppress("t")
    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building MacroEditScreen UI")

        val doneButton = rootComponent.childById(ButtonComponent::class.java, "done_button")
        val cancelButton = rootComponent.childById(ButtonComponent::class.java, "cancel_button")
        commandsLayout = rootComponent.childById(FlowLayout::class.java, "commands_panel")
        val keyBindButton = rootComponent.childById(ButtonComponent::class.java, "key_bind_button")
        val resetButton = rootComponent.childById(ButtonComponent::class.java, "reset_button")
        val macroName = rootComponent.childById(TextFieldWidget::class.java, "macro_name")
        val title = rootComponent.childById(LabelComponent::class.java, "title")

        // icons
        // textures/gui/macros/cross.png
        // textures/gui/macros/edit.png

        commandsLayout!!.gap(1)
        macroName.text = macro!!.name
        macroName.setCursorToStart(false)
        title.text(Text.translatable("redstone-helper.feature.macro.edit_macro_title", macro!!.name))

        if (new) {
            addCommand("/redstone-helper notification add-to-queue false hello world!")
            addCommand("")
        } else {
            macro!!.commands.forEach {
                addCommand(it)
            }
            addCommand("")
        }

        if (new) {
            keyBindButton.message = Text.translatable("redstone-helper.feature.macro.key_bind_none")
        } else {
            if (macro!!.key != GLFW.GLFW_KEY_UNKNOWN) {
                macro!!.key = macro!!.key
                keyBindButton.message = Text.literal(GLFW.glfwGetKeyName(macro!!.key, -1))
            } else {
                keyBindButton.message = Text.translatable("redstone-helper.feature.macro.key_bind_none")
            }
        }

        keyBindButton.onPress {
            val scope = CoroutineScope(Dispatchers.Default)

            keyBindButton.message = Text.literal(">").formatted(Formatting.YELLOW)
                .append(keyBindButton.message.copy().formatted(Formatting.UNDERLINE, Formatting.WHITE))
                .append(Text.literal("<").formatted(Formatting.YELLOW))

            var keyAssigned = false
            scope.launch {
                while (!keyAssigned) {
                    MinecraftClient.getInstance().execute {
                        for (key in GLFW.GLFW_KEY_SPACE..GLFW.GLFW_KEY_LAST) {
                            if (InputUtil.isKeyPressed(client!!.window.handle, key)) {
                                if (key == GLFW.GLFW_KEY_ESCAPE) {
                                    keyBindButton.message = Text.translatable("redstone-helper.feature.macro.key_bind_none")
                                    macro!!.key = GLFW.GLFW_KEY_UNKNOWN
                                } else {
                                    macro!!.key = key
                                    keyBindButton.message = Text.literal(GLFW.glfwGetKeyName(key, -1))
                                }
                                keyAssigned = true
                            }
                        }
                    }
                    delay(10)
                }
            }
        }

        resetButton.onPress {
            keyBindButton.message = Text.translatable("redstone-helper.feature.macro.key_bind_none")
        }

        macroName.setChangedListener {
            macro!!.name = macroName.text
            title.text(Text.translatable("redstone-helper.feature.macro.edit_macro_title", macro!!.name))
        }


        doneButton.onPress {
            // saving
            if (!new) {
                macro!!.commands.removeAll(macro!!.commands)
            }

            commandsLayout!!.children().forEach { flowLayout ->
                val command = (flowLayout as FlowLayout).childById(TextFieldWidget::class.java, "text_widget").text
                if (command != "") {
                    macro!!.commands.add(command)
                }
            }

            if (new) {
                macro!!.name = generateSequence(macro!!.name) { it + "_1" }
                    .first { Macros.readMacro(it) == null }
                Macros.addMacro(macro!!)
            } else {
                Macros.editMacro(macro!!.name, macro!!)
            }

            MacrosKeyBindings.updateMacros()
            parent!!.update()
            this.client!!.setScreen(parent)
        }

        cancelButton.onPress {
            this.client!!.setScreen(parent)
        }

    }

    @Suppress("t")
    private fun addCommand(command: String) {
        val id = (commandsLayout!!.children().size + 1).toString()

        @Suppress("DuplicatedCode")
        commandsLayout!!.child(
            Containers.horizontalFlow(Sizing.fill(), Sizing.fixed(32))
                .child(
                    Containers.horizontalFlow(Sizing.fixed(300), Sizing.fill())
                        .child(
                            Containers.horizontalFlow(Sizing.content(), Sizing.content())
                                .child(
                                    Components.button(Text.literal("")) {
                                        commandsLayout!!.removeChild(commandsLayout!!.childById(FlowLayout::class.java, id))
                                    }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                        .configure {
                                            if (commandsLayout!!.children().isEmpty()) {
                                                (it as ButtonComponent).visible = false
                                            }
                                        }
                                )
                                .child(
                                    Components.texture(
                                        Identifier.of(Constants.MOD_ID, "textures/gui/macros/cross.png"),
                                        0,
                                        0,
                                        250,
                                        250
                                    )
                                        .sizing(Sizing.fixed(20), Sizing.fixed(20))
                                        .positioning(Positioning.relative(0, 0))
                                        .configure {
                                            if (commandsLayout!!.children().isEmpty()) {
                                                (it as TextureComponent).visibleArea(PositionedRectangle.of(0, 0, Size.zero()))
                                            }
                                        }
                                )
                                .positioning(Positioning.relative(98, 50))
                        )
                        .child(
                            Components.textBox(Sizing.fixed(250))
                                .configure {
                                    val textWidget = (it as TextFieldWidget)
                                    textWidget.setMaxLength(999999999)
                                    textWidget.margins(Insets.left(5))
                                    textWidget.text = command
                                    textWidget.setCursorToStart(false)
                                    textWidget.id("text_widget")
                                    if (textWidget.text == "") {
                                        textWidget.setSuggestion(Text.translatable("redstone-helper.feature.macro.add_command").string)
                                    } else {
                                        textWidget.setSuggestion("")
                                    }
                                    textWidget.setChangedListener {
                                        if (textWidget.text == "") {
                                            textWidget.setSuggestion(Text.translatable("redstone-helper.feature.macro.add_command").string)
                                        } else {
                                            if (command == "" && (commandsLayout!!.children().last() as FlowLayout).childById(TextFieldWidget::class.java, "text_widget").text != "") {
                                                addCommand("")
                                            }
                                            textWidget.setSuggestion("")
                                        }
                                    }
                                }
                        )
                        .surface(Surface.flat(0xFF0F0F0F.toInt()))
                        .verticalAlignment(VerticalAlignment.CENTER)
                )
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .id(id)
        )
    }
}