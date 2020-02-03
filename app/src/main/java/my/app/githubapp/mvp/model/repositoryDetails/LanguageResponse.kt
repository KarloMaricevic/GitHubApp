package my.app.githubapp.mvp.model.repositoryDetails

import com.google.gson.annotations.SerializedName

data class LanguageResponse (
    val languages :  HashMap<String,Int>
)
