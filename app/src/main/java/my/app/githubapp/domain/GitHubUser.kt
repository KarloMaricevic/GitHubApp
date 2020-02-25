package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName
import java.util.*

class GitHubUser(
    id : Int,
    login : String,
    thumbnailUrl : String ,
    val htmlUrl : String,
    val name : String,
    val company : String?,
    val blog : String?,
    val location : String?,
    val email : String?,
    val bio : String?,
    val publicRepos : Int,
    val followers : Int,
    val following : Int,
    val createdAt : Date,
    val updatedAt : Date
) : BasicGitHubUser(login,id,thumbnailUrl)