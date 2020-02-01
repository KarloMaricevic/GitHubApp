package my.app.githubapp.mvp.model

import com.google.gson.annotations.SerializedName
import my.app.githubapp.domain.GitHubRepo

data class GitHubApiQueryResponse<T>(

    @SerializedName("total_count")
    val totalCountOfRepositories : Int,

    @SerializedName("incomplete_results")
    val isListIncomplete : Boolean,

    @SerializedName("items")
    val responseItems : List<T>
)