package my.app.githubapp.utils.desirilizer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.app.githubapp.mvp.model.retrofitService.responses.LanguageResponse
import java.lang.reflect.Type

class LanguageResponseDeserializer : JsonDeserializer<LanguageResponse> {

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