package my.app.githubapp.utils.desirilizer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.app.githubapp.domain.GitHubUser
import java.lang.reflect.Type

class GitHubUserDeserializer : JsonDeserializer<GitHubUser>  {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubUser {

        return Any() as GitHubUser
    }
}