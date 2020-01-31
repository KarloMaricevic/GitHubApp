package my.app.githubapp.domain

import com.google.gson.annotations.SerializedName

data class GitHubUser(
    @SerializedName("login") val login : String,
    @SerializedName("id") val id : Int,
    @SerializedName("avatar_url") val thumbnailUrl : String,
    @SerializedName("gravatar_id") val gravatarId : String,
    @SerializedName("url") val url : String,
    @SerializedName("gists_url") val gistsUrl : String,
    @SerializedName("name") val name : String,
    @SerializedName("company") val company : String,
    @SerializedName("blog") val blog : String,
    @SerializedName("location") val location : String,
    @SerializedName("email") val email : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("public_gists") val publicGists : Int
){

    constructor(login: String,id: Int,avatar_url: String) : this(login,id,avatar_url,"","","","","","","","","", 0 )
}