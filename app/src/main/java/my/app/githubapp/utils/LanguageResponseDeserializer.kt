package my.app.githubapp.utils

import com.google.gson.*
import my.app.githubapp.mvp.model.repositoryDetails.LanguageResponse
import java.lang.NullPointerException
import java.lang.reflect.Type

class LanguageResponseDeserializer :  JsonDeserializer<LanguageResponse>{

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LanguageResponse {

        val languageList = HashMap<String,Int>()
        if(json != null){
            for(language in json.asJsonObject.entrySet()){
                languageList[language.key] = language.value.asInt
            }
            return LanguageResponse(
                languageList
            )
        }
         throw NullPointerException("json language response error")
    }
}

/*
class LanguageResponseDeserializer :  JsonDeserializer<List<LanguageResponse>>{

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<LanguageResponse> {
        if(json != null){
            for(language in json.asJsonObject.entrySet()){
                return Any() as List<LanguageResponse>
            }
        }
        throw NullPointerException("json language response error")
    }
}*/
