package my.app.githubapp.mvp.model.retrofitService.userGitHubService

import io.reactivex.Single
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUserResponse
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUsersRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserGitHubService {

    @GET("users/{userLogin}")
    fun getUserInfo(@Path("userLogin") userLogin : String) : Single<GitHubUserResponse>

    @GET("users/{userLogin}/repos")
    fun getUsersRepositories(@Path("userLogin") userLogin: String) : Single<GitHubUsersRepositoriesResponse>
}