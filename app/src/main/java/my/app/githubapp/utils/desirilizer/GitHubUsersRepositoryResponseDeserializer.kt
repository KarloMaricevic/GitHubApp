package my.app.githubapp.utils.desirilizer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.GitHubRepoResponse
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUsersRepositoriesResponse
import java.lang.reflect.Type

class GitHubUsersRepositoryResponseDeserializer  : JsonDeserializer<GitHubUsersRepositoriesResponse>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubUsersRepositoriesResponse {

        val gitHubRepoList = arrayListOf<GitHubRepo>()
        val jsonArray = json!!.asJsonArray
        for(jsonObject in jsonArray){
            val gitHubRepo = Gson().fromJson<GitHubRepo>(jsonObject,GitHubRepo::class.java)
            gitHubRepoList.add(gitHubRepo)
        }
        return GitHubUsersRepositoriesResponse(gitHubRepoList)
    }
}