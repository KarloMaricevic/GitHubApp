package my.app.githubapp.utils.desirilizer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUserResponse
import java.lang.reflect.Type

class GitHubUserResponseDeserializer : JsonDeserializer<GitHubUserResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubUserResponse {
        val gitHubUser = Gson().fromJson<GitHubUser>(json,GitHubUser::class.java)
        return GitHubUserResponse(gitHubUser)
    }
}