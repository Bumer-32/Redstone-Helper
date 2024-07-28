package ua.pp.lumivoid.util

import net.minecraft.item.Item
import org.mariuszgromada.math.mxparser.Expression
import ua.pp.lumivoid.Constants

object Calculate {
    private val logger = Constants.LOGGER

    fun calc(expression: String): Double {
        logger.debug("Calculating expression: $expression")
        val result = Expression(expression).calculate()
        if (result.isNaN()) {
            logger.debug("Calculated expression is NaN")
            return result
        } else {
            logger.debug("Calculated expression: $result")
            return result
        }
    }

    fun convertToRadix10(input: String): String {
        logger.debug("Converting $input to radix 10")

        val regex = "(\\b[01]+\\b)|(\\b[0-9]+\\b)|(\\b[A-F]+\\b)".toRegex()

        val result = regex.replace(input) { matchResult ->
            val match = matchResult.value
            when {
                match.all { it in '0'..'1' } -> Integer.parseInt(match, 2).toString() // Radix 2
                match.all { it in 'A'..'F' } -> Integer.parseInt(match, 16).toString() // Radix 16
                else -> match // Radix 10
            }
        }

        return result
    }

    fun calculateRedstoneSignal(redstoneSignal: Int, item: Item, inventorySize: Int): Int {
        return ((redstoneSignal - 1) * item.maxCount * inventorySize / 14.0).toInt() + 1
    }

    fun hexToRGB(hex: Int): Quadruple<Float, Float, Float, Float> {
        val alpha = (hex shr 24 and 0xFF) / 255.0f
        val red = (hex shr 16 and 0xFF) / 255.0f
        val green = (hex shr 8 and 0xFF) / 255.0f
        val blue = (hex and 0xFF) / 255.0f
        return Quadruple(red, green, blue, alpha)
    }
}