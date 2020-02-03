package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName

data class GitHubRepoOwner(
    @SerializedName("login") val login : String,
    @SerializedName("id") val id : Int,
    @SerializedName("avatar_url") val thumbnailUrl : String
)