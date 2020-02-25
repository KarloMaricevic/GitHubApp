package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.LanguagePercentage
import my.app.githubapp.mvp.model.retrofitService.responses.LanguageResponse
import my.app.githubapp.utils.MathFunctions

object LanguageResponseMapper : MapperInterface<LanguageResponse,List<LanguagePercentage>> {

    override fun convert(objectToConvert: LanguageResponse): List<LanguagePercentage> {
        val languagePercentageList = arrayListOf<LanguagePercentage>()

        val total = getTotalNumberOfLanguages(objectToConvert)

        for(specificLanguageNumber in objectToConvert.languages){
            val languagePercentage = MathFunctions.getRoundedPercentage(specificLanguageNumber.value,total,2)
            languagePercentageList.add(LanguagePercentage(specificLanguageNumber.key,languagePercentage))
        }

        return languagePercentageList
    }

    private fun getTotalNumberOfLanguages(languageResponse: LanguageResponse) : Int{
        var total = 0
        for(language in languageResponse.languages){
            total += language.value
        }
        return total
    }
}