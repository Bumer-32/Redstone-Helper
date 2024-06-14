package ua.pp.lumivoid.util

import net.minecraft.text.Text
import org.mariuszgromada.math.mxparser.Expression
import ua.pp.lumivoid.Constants

object Calculate {
    private val logger = Constants.LOGGER

    fun calc(expression: String): Text {
        logger.debug("Calculating expression: $expression")
        val result = Expression(expression).calculate()
        if (result.isNaN()) {
            logger.debug("Calculated expression is NaN")
            return Text.translatable("gui.redstone-helper.invalid_expression")
        } else {
            logger.debug("Calculated expression: $result")
            return Text.translatable("gui.redstone-helper.result", result)
        }
    }
}