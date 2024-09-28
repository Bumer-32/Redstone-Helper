package ua.pp.lumivoid.gui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.CheckboxComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Color
import io.wispforest.owo.ui.core.Surface
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import ua.pp.lumivoid.Config
import ua.pp.lumivoid.Constants
import ua.pp.lumivoid.util.Calculate

@Suppress("UsePropertyAccessSyntax")
class BitsScreen: BaseUIModelScreen<FlowLayout>(FlowLayout::class.java, DataSource.asset(Identifier.of(Constants.MOD_ID, "bits_ui_model"))) {
    private val logger = Constants.LOGGER

    override fun build(rootComponent: FlowLayout) {
        logger.debug("Building AutowireScreen UI")

        val layout = rootComponent.childById(FlowLayout::class.java, "layout")

        val toBitText = rootComponent.childById(LabelComponent::class.java, "toBitText")
        val toBitExpressionField = rootComponent.childById(TextFieldWidget::class.java, "toBitExpression")
        val toBitResult = rootComponent.childById(LabelComponent::class.java, "toBitResult")

        val fromBitText = rootComponent.childById(LabelComponent::class.java, "fromBitText")
        val fromBitExpressionField = rootComponent.childById(TextFieldWidget::class.java, "fromBitExpression")
        val fromBitResult = rootComponent.childById(LabelComponent::class.java, "fromBitResult")

        val hex = rootComponent.childById(CheckboxComponent::class.java, "HEX")

        val bitExpressionField = rootComponent.childById(TextFieldWidget::class.java, "bitExpression")
        val bitResult = rootComponent.childById(LabelComponent::class.java, "bitResult")

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

        toBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_INVALIDEXPRESSION))
        fromBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_INVALIDEXPRESSION))
        bitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_INVALIDEXPRESSION))
        toBitExpressionField.setMaxLength(999999999)
        bitExpressionField.setMaxLength(999999999)
        fromBitExpressionField.setMaxLength(999999999)

        var radix = 2

        toBitExpressionField.setChangedListener {
            logger.debug("Transform ${toBitExpressionField.text} to bit")

            try {
                if (radix == 2) {
                    toBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_RESULT, Integer.toBinaryString(toBitExpressionField.text.toInt())))
                } else {
                    toBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_RESULT, "#" + Integer.toHexString(toBitExpressionField.text.toInt())))
                }
            } catch (e: NumberFormatException) {
                logger.error("Not a number entered $e")
                toBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_INVALIDEXPRESSION))
            }
        }
        fromBitExpressionField.setChangedListener {
            logger.debug("Transform ${fromBitExpressionField.text} from bit")

            try {
                fromBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_RESULT, Integer.parseInt(fromBitExpressionField.text, radix)))
            } catch (e: NumberFormatException) {
                fromBitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_INVALIDEXPRESSION))
            }
        }

        hex.onChanged { checked ->
            if (checked) {
                radix = 16
                toBitText.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_TOHEX))
                fromBitText.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_FROMHEX))
            } else {
                toBitText.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_TOBIT))
                fromBitText.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_FROMBIT))
                radix = 2
            }
        }

        //BIT calculator

        bitExpressionField.setChangedListener {
            logger.debug("Calculating new expression: ${bitExpressionField.text}")

            val calcResult = Calculate.calc(Calculate.convertToRadix10(bitExpressionField.text))
            if (calcResult.isNaN()) {
                bitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_INVALIDEXPRESSION))
            } else {
                bitResult.text(Text.translatable(Constants.LOCALIZEIDS.FEATURE_BITSOPERATIONS_BITRESULT, calcResult, Integer.toBinaryString(calcResult.toInt()), "#" + Integer.toHexString(calcResult.toInt())))
            }
        }
    }
}