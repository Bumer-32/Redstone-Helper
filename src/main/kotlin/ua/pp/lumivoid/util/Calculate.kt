package ua.pp.lumivoid.util

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
}