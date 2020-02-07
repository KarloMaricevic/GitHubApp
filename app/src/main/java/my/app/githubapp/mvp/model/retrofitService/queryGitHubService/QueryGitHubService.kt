package my.app.githubapp.mvp.model.retrofitService.queryGitHubService

import io.reactivex.Single
import my.app.githubapp.domain.BasicGitHubRepo
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.queryGitHubService.responceModel.GitHubApiQueryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QueryGitHubService{

    @GET("search/repositories")
    fun getReposWithQueryInName(@Query("q") query: String,@Query("per_page") pageSize : Int = 11,@Query("page") pageNumber : Int = 1) : Single<GitHubApiQueryResponse<BasicGitHubRepo>>
}