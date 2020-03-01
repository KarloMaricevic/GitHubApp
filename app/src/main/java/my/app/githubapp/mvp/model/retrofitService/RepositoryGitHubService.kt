package my.app.githubapp.mvp.model.retrofitService

import io.reactivex.Observable
import io.reactivex.Single
import my.app.githubapp.mvp.model.retrofitService.responses.BasicGitHubUserResponse
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoContributorResponse
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoResponse
import my.app.githubapp.mvp.model.retrofitService.responses.LanguageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryGitHubService {

    @GET("repos/{owner}/{repoName}")
    fun getRepositoryInformation(
        @Path("owner") ownerLogin: String,
        @Path("repoName") repoName: String
    ): Single<GitHubRepoResponse>

    @GET("repos/{owner}/{repo}/subscribers")
    fun getRepoWatchers(
        @Path("owner") ownerLogin: String,
        @Path("repo") repoName: String
    ): Observable<List<BasicGitHubUserResponse>>

    @GET("repos/{owner}/{repoName}/languages")
    fun getRepositoryLanguage(
        @Path("owner") ownerLogin: String,
        @Path("repoName") repoName: String
    ): Single<LanguageResponse>

    @GET("repos/{owner}/{repoName}/contributors")
    fun getRepositoryContributors(
        @Path("owner") ownerLogin: String,
        @Path("repoName") repoName: String
    ): Single<List<GitHubRepoContributorResponse>>
}
