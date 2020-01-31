package my.app.githubapp.mvp.model

import com.google.gson.annotations.SerializedName
import my.app.githubapp.domain.GitHubRepo

data class GitHubApiQueryResponse<T>(

    @SerializedName("total_cont")
    val totalCountOfRepositorys : Double,

    @SerializedName("incomplete_results")
    val isListIncomplete : Boolean,

    @SerializedName("items")
    val responseItems : List<T>
)