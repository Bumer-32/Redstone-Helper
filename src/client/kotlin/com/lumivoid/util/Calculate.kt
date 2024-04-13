package com.lumivoid.util

import com.lumivoid.Constants
import org.mariuszgromada.math.mxparser.Expression
import org.slf4j.LoggerFactory

class Calculate {
    private val logger = LoggerFactory.getLogger(Constants().MOD_ID)
    fun calc(expression: String): Any {
        logger.debug("Calculating expression: $expression")
        val result = Expression(expression).calculate()
        if (result.isNaN()) {
            logger.debug("Calculated expression is NaN")
            return "Result: Invalid expression"
        } else {
            logger.debug("Calculated expression: $result")
            return "Result: $result"
        }
    }
}