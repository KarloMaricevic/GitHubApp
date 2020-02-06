package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUsersRepositoriesResponse

object GitHubUsersRepositoriesMapper {

    fun toGitHubRepositoriesList(gitHubUsersRepositoriesResponse: GitHubUsersRepositoriesResponse) : List<GitHubRepo>{
        return gitHubUsersRepositoriesResponse.gitHubRepoList
    }

}