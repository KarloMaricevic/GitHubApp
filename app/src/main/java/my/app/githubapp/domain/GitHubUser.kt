package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName
import java.util.*

data class GitHubUser(
    @SerializedName("login") val login : String,
    @SerializedName("id") val id : Int,
    @SerializedName("node_id") val node_id : String,
    @SerializedName("avatar_url") val avatarUrl : String,
    @SerializedName("gravatar_id") val gravatarId : String,
    @SerializedName("html_url") val htmlUrl : String,
    @SerializedName("name") val name : String,
    @SerializedName("company") val company : String,
    @SerializedName("blog") val blog : String,
    @SerializedName("location") val location : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("bio") val bio : String?,
    @SerializedName("public_repos") val publicRepos : Int,
    @SerializedName("followers") val followers : Int,
    @SerializedName("following") val following : Int,
    @SerializedName("created_at") val createdAt : Date,
    @SerializedName("updated_at") val updatedAt : Date
)