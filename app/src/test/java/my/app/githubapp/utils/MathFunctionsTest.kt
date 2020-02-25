package my.app.githubapp.utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IndexOutOfBoundsException


class MathFunctionsTest {

    @Nested
    inner class GetPercentageMethodTest{

        private val numberSmallerThanZero = -1
        private val numberEqualsZero = 0
        private val numberBiggerThanZero = 1

        private val testNumerator = 45
        private val testDenominator = 101
        private val expectedValue = 44.55
        private val delta = 0.01

        @Test
        fun throwsExceptionWhenDenominatorLessThanZero(){
            assertThrows<IndexOutOfBoundsException> { MathFunctions.getPercentage(numberBiggerThanZero,numberSmallerThanZero)  }
        }

        @Test
        fun throwsExceptionWhenDenominatorEqualsZero(){
            assertThrows<IndexOutOfBoundsException> { MathFunctions.getPercentage(numberBiggerThanZero,numberEqualsZero)  }
        }

        @Test
        fun throwsExceptionWhenNumeratorLessThanZero(){
            assertThrows<IndexOutOfBoundsException> { MathFunctions.getPercentage(numberSmallerThanZero,numberBiggerThanZero) }
        }

        @Test
        fun getsRightValueForValidArguments(){
            val actualValue = MathFunctions.getPercentage(testNumerator,testDenominator)
            assertEquals(expectedValue,actualValue,delta)
        }
    }


    @Nested
    inner class GetRoundedPercentageMethodTest{
        private val testNumerator = 45
        private val testDenominator = 101
        private val numOfDecimalPlaces = 2
        private val expectedValue = 44.55



        @Test
        fun getsRightValueForValidArguments(){
            val actualValue = MathFunctions.getRoundedPercentage(testNumerator,testDenominator,numOfDecimalPlaces)
            assertEquals(expectedValue,actualValue)
        }
    }

    @Nested
    inner class RoundToDecimalsMethodTest{
        private val testValue = 101.45622
        private val numOfDecimalPlaces = 2
        private val expectedValue = 101.46


        @Test
        fun getRightValueForArguments(){
            val actualValue = MathFunctions.roundToDecimals(testValue,numOfDecimalPlaces)
            assertEquals(expectedValue,actualValue)
        }
    }
}
