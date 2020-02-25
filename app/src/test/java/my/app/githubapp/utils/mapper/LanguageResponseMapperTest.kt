package my.app.githubapp.utils.mapper

import com.google.common.truth.Truth
import my.app.githubapp.domain.LanguagePercentage
import my.app.githubapp.mvp.model.retrofitService.responses.LanguageResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LanguageResponseMapperTest {

    @Test
    fun getEmptyListWhenLanguageResponseHashMapEmpty(){
        val emptyLanguageResponse =
            LanguageResponse(
                hashMapOf()
            )
        val expectedValue = listOf<LanguagePercentage>()


        val actualValue = LanguageResponseMapper.convert(emptyLanguageResponse)
        assertEquals(expectedValue,actualValue)


    }

    @Test
    fun getLanguagePercentageListWhenLanguageResponseHashMapIsPopulated(){
        val populatedLanguageResponse =
            LanguageResponse(
                hashMapOf(
                    Pair("LanguageName1", 1434234),
                    Pair("LanguageName2", 123311),
                    Pair("LanguageName3", 1232)
                )
            )
        val expectedValue = listOf(
            LanguagePercentage("LanguageName2",7.91),
            LanguagePercentage("LanguageName1",92.01),
            LanguagePercentage("LanguageName3",0.08)
        )

        val actualValue = LanguageResponseMapper.convert(populatedLanguageResponse)

        Truth.assertThat(actualValue).containsExactlyElementsIn(expectedValue)
    }


}