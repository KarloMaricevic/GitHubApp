package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName

open class BasicGitHubRepo(
    @SerializedName("name")
    val name : String,

    @SerializedName("id")
    val id : Int,

    @SerializedName("owner")
    val owner : BasicGitHubUser
)