package my.app.githubapp.mvp.model.repositorySearch

import io.reactivex.Observable
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.GitHubApiQueryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositorySearchGitHubService{


    @GET("search/repositories")
    fun getReposWithQueryInName(@Query("q") query: String) : Observable<GitHubApiQueryResponse<GitHubRepo>>


    @GET("repos/{owner}/{repo}/subscribers")
    fun getRepoWatchers(@Path("owner") ownerName: String,@Path("repo") repoName : String) : Observable<List<GitHubUser>>

}