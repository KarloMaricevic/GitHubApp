package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.model.repositoryDetails.LanguageResponse
import my.app.githubapp.utils.MathFunctions

object LanguageResponseMapper {

    fun toLanguagePercentileList(languageResponse: LanguageResponse) : List<LanguagePercentile>{

        var total = 0
        var languagePercentileList = arrayListOf<LanguagePercentile>()


        for(language in languageResponse.languages){
            total+= language.value
        }

        for(language in languageResponse.languages){
            val percentile = (language.value.toFloat() / total * 100).toDouble()
            languagePercentileList.add(LanguagePercentile(language.key,MathFunctions.roundToDecimals(percentile,2).toFloat()))
        }
       return languagePercentileList
    }
}