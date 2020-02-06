package my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel

import my.app.githubapp.domain.GitHubRepo

data class GitHubUsersRepositoriesResponse (
    val gitHubRepoList : List<GitHubRepo>
)