package ua.pp.lumivoid.util

import net.minecraft.inventory.Inventory
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

    fun calculateRedstoneSignal(redstoneSignal: Int, item: Item, inventory: Inventory): Int {
        return ((redstoneSignal - 1) * item.maxCount * inventory.size() / 14.0).toInt() + 1
    }
}