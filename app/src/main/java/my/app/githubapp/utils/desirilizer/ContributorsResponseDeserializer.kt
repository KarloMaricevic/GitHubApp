package my.app.githubapp.utils.desirilizer

import com.google.gson.*
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

        if(json == null)
            return ContributorsResponse(
                gitHubContributorList
            )
        else{
            val jsonArray = json.asJsonArray

            for(jsonElement in jsonArray){
                val numberOfCommits = jsonElement.asJsonObject.getAsJsonPrimitive("total").asInt
                val authorJsonObject =  jsonElement.asJsonObject.getAsJsonObject("author")
                val authorName = authorJsonObject.getAsJsonPrimitive("login").asString
                val authorId = authorJsonObject.getAsJsonPrimitive("id").asInt
                val authorThumbnailLink = authorJsonObject.getAsJsonPrimitive("avatar_url").asString
                gitHubContributorList.add(GitHubContributor(authorName,authorId,authorThumbnailLink,numberOfCommits))
            }
        }
        return ContributorsResponse(
            gitHubContributorList
        )
    }
}

