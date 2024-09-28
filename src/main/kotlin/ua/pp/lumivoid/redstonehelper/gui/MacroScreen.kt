package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.CheckboxComponent
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
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.tinyfd.TinyFileDialogs
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.keybindings.MacrosKeyBindings
import ua.pp.lumivoid.owocomponents.TexturedButton
import ua.pp.lumivoid.util.Macro
import ua.pp.lumivoid.util.features.Macros
import java.util.concurrent.CompletableFuture

class MacroScreen(private val parent: Screen?): BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "macro_ui_model"))) {
    private val logger = Constants.LOGGER

    private var macrosLayout: FlowLayout? = null
    private var loading: LabelComponent? = null

    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building MacroScreen UI")

//        val mainPanel = rootComponent.childById(FlowLayout::class.java, "main-panel")
        loading = rootComponent.childById(LabelComponent::class.java, "loading")
        val instrumentsContainer = rootComponent.childById(FlowLayout::class.java, "instruments_container")
        val newMacroButton = rootComponent.childById(ButtonComponent::class.java, "new_macro_button")
        val doneButton = rootComponent.childById(ButtonComponent::class.java, "done_button")
        macrosLayout = rootComponent.childById(FlowLayout::class.java, "macros_panel")

        instrumentsContainer.child(0,
            TexturedButton(Identifier.of(Constants.MOD_ID, "export_all")) {
                export(Macros.listMacros().map { it.name }.toMutableList())
            }.sizing(Sizing.fixed(20)).tooltip(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_TOOLTIP_EXPORTALL)).margins(Insets.right(5))
        )
        instrumentsContainer.child(1,
            TexturedButton(Identifier.of(Constants.MOD_ID, "import")) {
                import()
            }.sizing(Sizing.fixed(20)).tooltip(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_TOOLTIP_IMPORT)).margins(Insets.right(5))
        )

        macrosLayout!!.gap(1)

        update()

        newMacroButton.onPress {
            this.client!!.setScreen(MacroEditScreen(this, "My Super Macro", true))
        }

        doneButton.onPress {
            if (parent == null) {
                this.close()
            } else {
                this.client!!.setScreen(parent)
            }
        }
    }

    private fun addMacro(name: String, enabled: Boolean) {
        @Suppress("DuplicatedCode")
        macrosLayout!!.child(
            Containers.horizontalFlow(Sizing.fill(), Sizing.fixed(32))
                .child(
                    Containers.horizontalFlow(Sizing.fixed(450), Sizing.fill())
                        .child(
                            Containers.horizontalFlow(Sizing.content(), Sizing.content())
                                .configure { layout: FlowLayout ->
                                    layout.positioning(Positioning.relative(100, 50))
                                    layout.margins(Insets.right(10))

                                    layout.child(
                                        TexturedButton(Identifier.of(Constants.MOD_ID, "export")) {
                                            export(mutableListOf(name))
                                        }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                            .margins(Insets.right(5))
                                            .tooltip(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_TOOLTIP_EXPORT))
                                    )

                                    layout.child(
                                        TexturedButton(Identifier.of(Constants.MOD_ID, "pencil")) {
                                            this.client!!.setScreen(MacroEditScreen(this, name, false))
                                        }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                            .margins(Insets.right(5))
                                            .tooltip(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_TOOLTIP_EDIT))
                                    )

                                    layout.child(
                                        TexturedButton(Identifier.of(Constants.MOD_ID, "cross")) {
                                            Macros.removeMacro(name)
                                            update()
                                        }.sizing(Sizing.fixed(20), Sizing.fixed(20))
                                            .tooltip(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_TOOLTIP_DELETE))
                                    )
                                }
                        )
                        .child(
                            Containers.horizontalFlow(Sizing.fixed(350), Sizing.content())
                                .child(
                                    Components.checkbox(Text.empty())
                                        .tooltip(Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_TOOLTIP_ENABLEDFORKEYBINDS))
                                        .margins(Insets.top(1))
                                        .configure {
                                            val checkBox = it as CheckboxComponent
                                            checkBox.checked(enabled)
                                            checkBox.onChanged { checked ->
                                                logger.debug("Macro $name enabled: $checked")
                                                val macro = Macros.readMacro(name)
                                                macro!!.enabled = checked
                                                Macros.editMacro(name, macro)
                                                MacrosKeyBindings.updateMacros()
                                                update()
                                            }
                                        }
                                )
                                .child(
                                    Components.label(Text.literal(name))
                                        .margins(Insets.of(5, 0, 5, 0))  //checkbox buggy and already has margin
                                )
                                .positioning(Positioning.relative(0, 50))
                                .margins(Insets.left(10))

                        )
                        .surface(Surface.flat(0xFF0F0F0F.toInt()))
                        .verticalAlignment(VerticalAlignment.CENTER)
                )
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .id(name)

        )
    }

    private fun import() {
        val checkList = Macros.listMacros().toString()

        // Importing
        CompletableFuture.runAsync({
            MemoryStack.stackPush().use { stack ->
                val filters = stack.mallocPointer(1)
                filters.put(stack.UTF8("*.json"))
                filters.flip()
                // animation
                var animateLoading = true

                CoroutineScope(Dispatchers.Default).launch {

                    MinecraftClient.getInstance().submit {
                        loading!!.text(Text.literal("●"))
                    }

                    while (animateLoading) {
                        loading!!.positioning().animate(1000, Easing.EXPO, Positioning.relative(55, 50)).forwards()
                        delay(1000)
                        loading!!.positioning().animate(1000, Easing.EXPO, Positioning.relative(45, 50)).forwards()
                        delay(1000)
                    }
                    loading!!.positioning().animate(1000, Easing.EXPO, Positioning.relative(50, 50)).forwards()
                    delay(1000)
                    MinecraftClient.getInstance().submit {
                        loading!!.text(Text.literal(""))
                    }
                }

                // File chooser
                var path = TinyFileDialogs.tinyfd_openFileDialog(
                    Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_DIALOG_IMPORT).string,
                    null,
                    null,
                    Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_DIALOG_FILENAME).string,
                    true
                )

                if (path != null) {
                    val paths = path.split("|")

                    paths.forEach { macroPath ->
                        logger.info("Importing macros from $macroPath")
                        val importedMacros = Macros.importMacro(macroPath)
                        importedMacros?.forEach { macro ->
                            macro.enabled = false // Import ALWAYS disabled
                            macro.name = generateSequence(macro.name) { it + "_1" } // Don't allow to create macros with same name
                                .first { Macros.readMacro(it) == null }
                            Macros.addMacro(macro)
                        }
                    }
                } else {
                    logger.info("Import canceled")
                }

                animateLoading = false
            }
        }, Util.getMainWorkerExecutor()).whenComplete { unused, throwable ->
            logger.info("End of import")
            if (checkList != Macros.listMacros().toString()) {
                MinecraftClient.getInstance().submit { // In RENDER THREAD!!! IT'S VERY IMPORTANT
                    logger.info("Update")
                    update()
                }
            }
        }
    }

    private fun export(macros: MutableList<String>) {
        // Exporting
        CompletableFuture.runAsync({
            MemoryStack.stackPush().use { stack ->
                val filters = stack.mallocPointer(1)
                filters.put(stack.UTF8("*.json"))
                filters.flip()

                val path: String? = TinyFileDialogs.tinyfd_saveFileDialog(
                    Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_DIALOG_EXPORT).string,
                    null,
                    filters,
                    Text.translatable(Constants.LOCALIZEIDS.FEATURE_MACRO_DIALOG_FILENAME).string
                )

                if (path != null) {
                    macros.forEachIndexed { index, macro ->
                        logger.info("Exporting macro $macro to $path:$index")
                    }
                    Macros.exportMacro(path, macros)
                } else {
                    logger.info("Export canceled")
                }
            }
        }, Util.getMainWorkerExecutor()).whenComplete { unused, throwable ->
            logger.info("End of export")
        }
    }

    fun update() {
        macrosLayout!!.clearChildren()
        Macros.listMacros().forEach { macro: Macro ->
            addMacro(macro.name, macro.enabled)
        }
    }
}

