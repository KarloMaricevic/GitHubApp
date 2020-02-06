package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName
import java.util.*

class GitHubUser(
    id : Int,
    login : String,
    thumbnailUrl : String ,
    @SerializedName("html_url") val htmlUrl : String,
    @SerializedName("name") val name : String,
    @SerializedName("company") val company : String?,
    @SerializedName("blog") val blog : String?,
    @SerializedName("location") val location : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("bio") val bio : String?,
    @SerializedName("public_repos") val publicRepos : Int,
    @SerializedName("followers") val followers : Int,
    @SerializedName("following") val following : Int,
    @SerializedName("created_at") val createdAt : Date,
    @SerializedName("updated_at") val updatedAt : Date
) : BasicGitHubUser(login,id,thumbnailUrl)