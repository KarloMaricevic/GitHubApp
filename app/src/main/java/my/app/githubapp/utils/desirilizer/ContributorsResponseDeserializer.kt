package my.app.githubapp.utils.desirilizer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.ContributorsResponse
import java.lang.reflect.Type

class ContributorsResponseDeserializer : JsonDeserializer<ContributorsResponse?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ContributorsResponse? {

        val gitHubContributorList = arrayListOf<GitHubContributor>()


        if(json != null){
            val jsonArray = json.asJsonArray

            for(jsonElement in jsonArray){
                val numberOfCommits = jsonElement.asJsonObject.getAsJsonPrimitive("total").asInt
                val authorJsonObject =  jsonElement.asJsonObject.getAsJsonObject("author")
                val authorBasicInfo = Gson().fromJson<BasicGitHubUser>(authorJsonObject,BasicGitHubUser::class.java)
                gitHubContributorList.add(GitHubContributor(authorBasicInfo,numberOfCommits))
            }
        }
        return ContributorsResponse(gitHubContributorList)
    }
}

