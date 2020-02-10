package my.app.githubapp.utils.desirilizer

import com.google.gson.*
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.LanguageResponse
import my.app.githubapp.utils.MathFunctions
import java.lang.NullPointerException
import java.lang.reflect.Type

class LanguageResponseDeserializer :  JsonDeserializer<LanguageResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LanguageResponse {


        val languageList = HashMap<String, Int>()
        if (json != null) {
            for (language in json.asJsonObject.entrySet()) {
                languageList[language.key] = language.value.asInt
            }
            return LanguageResponse(
                languageList
            )
        }
        throw NullPointerException("json language response error")
    }
}