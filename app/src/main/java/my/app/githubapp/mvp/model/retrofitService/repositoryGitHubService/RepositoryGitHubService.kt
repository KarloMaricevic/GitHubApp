package my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService

import io.reactivex.Observable
import io.reactivex.Single
import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.ContributorsResponse
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.LanguageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryGitHubService{

    @GET("repos/{owner}/{repoName}")
    fun getRepositoryInformation(@Path("owner") ownerLogin: String, @Path("repoName") repoName: String) : Single<GitHubRepo>

    @GET("repos/{owner}/{repo}/subscribers")
    fun getRepoWatchers(@Path("owner") ownerLogin: String,@Path("repo") repoName : String) : Observable<List<BasicGitHubUser>>

    @GET("repos/{owner}/{repoName}/languages")
    fun getRepositoryLanguage(@Path("owner") ownerLogin : String,@Path("repoName") repoName : String) : Single<LanguageResponse>

    @GET("repos/{owner}/{repoName}/stats/contributors")
    fun getRepositoryContributors(@Path("owner") ownerLogin: String, @Path("repoName") repoName: String) : Single<ContributorsResponse>
}