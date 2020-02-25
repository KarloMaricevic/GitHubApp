package my.app.githubapp.utils

import kotlin.math.pow

object MathFunctions {

    fun getPercentage(numerator: Int, denominator: Int): Double {
        if (denominator <= 0) {
            throw IndexOutOfBoundsException("denominator can't be less or equals to 0")
        }
        if (numerator < 0) {
            throw IndexOutOfBoundsException("numerator can't be less than 0")
        } else {
            return (numerator.toDouble() / denominator) * 100
        }
    }

    fun getRoundedPercentage(numerator: Int, denominator: Int, numOfDecimalPlaces: Int): Double {
        val longPercentage = getPercentage(numerator, denominator)
        return roundToDecimals(longPercentage, numOfDecimalPlaces)
    }

    fun roundToDecimals(number: Double, numOfDecimalPlaces: Int): Double {
        val factor = 10.0.pow(numOfDecimalPlaces.toDouble())
        return Math.round(number * factor) / factor
    }

}