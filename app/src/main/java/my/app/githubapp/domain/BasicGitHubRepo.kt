package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName

open class BasicGitHubRepo(
    val name : String,
    val id : Int,
    val owner : BasicGitHubUser
)