package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName
import java.util.*

class GitHubRepo(
    name : String,
    id : Int,
    owner : BasicGitHubUser,
    @SerializedName("html_url")
    val pageUrl : String,
    @SerializedName("watchers_count")
    val staredNumber : Int,
    @SerializedName("forks_count")
    val forks : Int,
    @SerializedName("open_issues_count")
    val openIssues : Int,
    @SerializedName("stargazers_count")
    val watcherNumber : Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("language")
    val language : String?
    ) : BasicGitHubRepo(name,id,owner)