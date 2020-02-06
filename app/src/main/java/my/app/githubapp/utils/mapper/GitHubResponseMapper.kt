package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.GitHubRepoResponse

object GitHubResponseMapper {

    fun toGitHubRepo(gitHubRepoResponse: GitHubRepoResponse) : GitHubRepo{
        return gitHubRepoResponse.gitHubRepo
    }
}