package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName

open class BasicGitHubUser(
    val login : String,
    val id : Int,
    val thumbnailUrl : String
)