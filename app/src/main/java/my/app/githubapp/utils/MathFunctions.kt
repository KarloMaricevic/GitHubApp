package my.app.githubapp.utils

import kotlin.math.pow

object MathFunctions {
    fun roundToDecimals(number: Double, numDecimalPlaces: Int): Double {
        val factor = 10.0.pow(numDecimalPlaces.toDouble())
        return Math.round(number * factor) / factor
    }

}