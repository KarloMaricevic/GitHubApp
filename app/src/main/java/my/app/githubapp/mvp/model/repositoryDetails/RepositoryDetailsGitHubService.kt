package my.app.githubapp.mvp.model.repositoryDetails

import io.reactivex.Observable
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubRepoOwner
import my.app.githubapp.domain.GitHubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryDetailsGitHubService {
    @GET("repos/{owner}/{repoName}")
    fun getRepositoryInformation(@Path("owner") owner : String,@Path("repoName") retpName : String) : Observable<GitHubRepo>

    @GET("repos/{owner}/{repo}/subscribers")
    fun getRepoWatchers(@Path("owner") ownerName: String,@Path("repo") repoName : String) : Observable<List<GitHubRepoOwner>>

    @GET("repos/{owner}/{repoName}/languages")
    fun getRepositoryLanguage(@Path("owner") owner : String,@Path("repoName") repoName : String) : Observable<LanguageResponse>

    @GET("users/{ownerLogin}")
    fun getUserInfo(@Path("ownerLogin") ownerLogin : String) : Observable<GitHubUser>
}