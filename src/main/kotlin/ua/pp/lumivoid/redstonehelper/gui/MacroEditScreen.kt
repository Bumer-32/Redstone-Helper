package ua.pp.lumivoid.redstonehelper.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.component.LabelComponent
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
import ua.pp.lumivoid.redstonehelper.Constants
import ua.pp.lumivoid.redstonehelper.keybindings.MacrosKeyBindings
import ua.pp.lumivoid.redstonehelper.owocomponents.TexturedButton
import ua.pp.lumivoid.redstonehelper.util.Macro
import ua.pp.lumivoid.redstonehelper.util.features.Macros


@Suppress("UsePropertyAccessSyntax")
class MacroEditScreen(private val parent: MacroScreen?, name:String, private val new: Boolean): BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "macro_edit_ui_model"))) {
    private val logger = Constants.LOGGER
    
    private var commandsLayout: FlowLayout? = null
    private var keyBindButton: ButtonComponent? = null
    private var macro: Macro? = null

    private var keyAssigned = true

    init {
        macro = if (!new) {
            Macros.readMacro(name)
        } else {
            Macro(name, GLFW.GLFW_KEY_UNKNOWN, false, mutableListOf())
        }
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }

    @Suppress("t")
    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building MacroEditScreen UI")

        val doneButton = rootComponent.childById(ButtonComponent::class.java, "done_button")
        val cancelButton = rootComponent.childById(ButtonComponent::class.java, "cancel_button")
        commandsLayout = rootComponent.childById(FlowLayout::class.java, "commands_panel")
        keyBindButton = rootComponent.childById(ButtonComponent::class.java, "key_bind_button")
        val resetButton = rootComponent.childById(ButtonComponent::class.java, "reset_button")
        val macroName = rootComponent.childById(TextFieldWidget::class.java, "macro_name")
        val title = rootComponent.childById(LabelComponent::class.java, "title")

        // icons
        // textures/gui/macros/cross.png
        // textures/gui/macros/edit.png

        commandsLayout!!.gap(1)
        macroName.text = macro!!.name
        macroName.setCursorToStart(false)
        macroName.setMaxLength(999999999)
        title.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_EDITMACROTITLE, macro!!.name))

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
            keyBindButton!!.message = Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_KEYBINDNONE)
        } else {
            if (macro!!.key != GLFW.GLFW_KEY_UNKNOWN) {
                macro!!.key = macro!!.key
                keyBindButton!!.message = Text.literal(GLFW.glfwGetKeyName(macro!!.key, -1))
            } else {
                keyBindButton!!.message = Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_KEYBINDNONE)
            }
        }

        keyBindButton!!.onPress {
            checkForAssigningActive()

            val scope = CoroutineScope(Dispatchers.Default)

            keyAssigned = false

            keyBindButton!!.message = Text.literal(">").formatted(Formatting.YELLOW)
                .append(keyBindButton!!.message.copy().formatted(Formatting.UNDERLINE, Formatting.WHITE))
                .append(Text.literal("<").formatted(Formatting.YELLOW))

            scope.launch {
                while (!keyAssigned) {
                    MinecraftClient.getInstance().execute {
                        for (key in GLFW.GLFW_KEY_SPACE..GLFW.GLFW_KEY_LAST) {
                            if (InputUtil.isKeyPressed(client!!.window.handle, key)) {
                                if (key == GLFW.GLFW_KEY_ESCAPE) {
                                    keyBindButton!!.message = Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_KEYBINDNONE)
                                    macro!!.key = GLFW.GLFW_KEY_UNKNOWN
                                } else {
                                    macro!!.key = key
                                    keyBindButton!!.message = Text.literal(GLFW.glfwGetKeyName(key, -1))
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
            checkForAssigningActive()
            keyBindButton!!.message = Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_KEYBINDNONE)
            macro!!.key = GLFW.GLFW_KEY_UNKNOWN
        }

        macroName.setChangedListener {
            checkForAssigningActive()
            macro!!.name = macroName.text
            title.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_EDITMACROTITLE, macro!!.name))
        }


        doneButton.onPress {
            checkForAssigningActive()

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

            macro!!.name = generateSequence(macro!!.name) { it + "_1" } // Don't allow to create macros with same name
                .first { Macros.readMacro(it) == null }

            if (new) {
                Macros.addMacro(macro!!)
            } else {
                Macros.editMacro(macro!!.name, macro!!)
            }

            MacrosKeyBindings.updateMacros()
            parent!!.update()
            this.client!!.setScreen(parent)
        }

        cancelButton.onPress {
            checkForAssigningActive()
            this.client!!.setScreen(parent)
        }

    }

    /**
    * Use to stop searching assigning key
    * e.g: we started key search but didn't press any key on keyboard and press done button, then we should to stop searching and set key for none
    */
    private fun checkForAssigningActive() {
        if (!keyAssigned) {
            logger.warn("stop assigning")
            keyAssigned = false
            macro!!.key = GLFW.GLFW_KEY_UNKNOWN

            keyBindButton!!.message = Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_KEYBINDNONE)
        }
    }

    @Suppress("t")
    private fun addCommand(command: String) {
        val id = (commandsLayout!!.children().size + 1).toString()

        @Suppress("DuplicatedCode")
        commandsLayout!!.child(
            Containers.horizontalFlow(Sizing.fill(), Sizing.fixed(32))
                .child(
                    Containers.horizontalFlow(Sizing.fixed(450), Sizing.fill())
                        .child(
                            TexturedButton(Identifier.of(Constants.MOD_ID, "cross")) {
                                commandsLayout!!.removeChild(commandsLayout!!.childById(FlowLayout::class.java, id))
                            }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                .positioning(Positioning.relative(98, 50))
                                .configure {
                                    if (commandsLayout!!.children().isEmpty()) {
                                        (it as ButtonComponent).visible = false
                                    }
                                }
                        )
                        .child(
                            Components.textBox(Sizing.fixed(400))
                                .configure {
                                    val textWidget = (it as TextFieldWidget)
                                    textWidget.setMaxLength(999999999)
                                    textWidget.margins(Insets.left(5))
                                    textWidget.text = command
                                    textWidget.setCursorToStart(false)
                                    textWidget.id("text_widget")
                                    textWidget.setMaxLength(256)
                                    if (textWidget.text == "") {
                                        textWidget.setSuggestion(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_ADDCOMMAND).string)
                                    } else {
                                        textWidget.setSuggestion("")
                                    }
                                    if (commandsLayout!!.children().isEmpty()) {
                                        textWidget.sizing(Sizing.fixed(440), Sizing.fixed(20))
                                    }
                                    textWidget.setChangedListener {
                                        checkForAssigningActive()
                                        if (textWidget.text == "") {
                                            textWidget.setSuggestion(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_ADDCOMMAND).string)
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