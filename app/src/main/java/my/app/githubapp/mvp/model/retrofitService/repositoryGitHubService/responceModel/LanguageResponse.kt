package my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel

import com.google.gson.annotations.SerializedName

data class LanguageResponse (
    val languages :  HashMap<String,Int>
)
