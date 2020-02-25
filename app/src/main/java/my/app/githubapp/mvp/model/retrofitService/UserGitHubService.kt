package my.app.githubapp.mvp.model.retrofitService

import io.reactivex.Single
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoResponse
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserGitHubService {

    @GET("users/{userLogin}")
    fun getUserInfo(@Path("userLogin") userLogin: String): Single<GitHubUserResponse>

    @GET("users/{userLogin}/repos")
    fun getUsersRepositories(@Path("userLogin") userLogin: String): Single<List<GitHubRepoResponse>>
}