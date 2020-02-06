package my.app.githubapp.utils.desirilizer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.GitHubRepoResponse
import java.lang.reflect.Type

class GitHubRepoResponseDeserializer : JsonDeserializer<GitHubRepoResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubRepoResponse {

        val gitHubRepo  = Gson().fromJson<GitHubRepo>(json, GitHubRepo::class.java)
        return GitHubRepoResponse(
            gitHubRepo
        )
    }
}