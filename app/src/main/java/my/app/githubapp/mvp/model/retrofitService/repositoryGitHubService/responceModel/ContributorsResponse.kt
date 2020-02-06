package my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel

import my.app.githubapp.domain.GitHubContributor

data class ContributorsResponse(
    val gitHubContributor: List<GitHubContributor>
)