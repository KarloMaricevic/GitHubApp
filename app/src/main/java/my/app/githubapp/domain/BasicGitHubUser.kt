package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName

open class BasicGitHubUser(
    @SerializedName("login")
    val login : String,
    @SerializedName("id")
    val id : Int,
    @SerializedName("avatar_url")
    val thumbnailUrl : String
)