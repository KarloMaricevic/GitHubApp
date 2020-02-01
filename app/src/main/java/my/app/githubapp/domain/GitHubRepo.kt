package my.app.githubapp.domain

import com.bumptech.glide.annotation.Excludes
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class GitHubRepo(

    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id : Int,
    @SerializedName("updated_at")
    val lastUpdateDate: Date,
    @SerializedName("owner")
    val owner : GitHubUser,
    @SerializedName("watchers_count")
    val staredNumber : Int,
    @SerializedName("forks_count")
    val forks : Int,
    @SerializedName("open_issues_count")
    val openIssues : Int,
    @Expose(deserialize = false,serialize = false)
    val watcherNumber : Int? = null
)