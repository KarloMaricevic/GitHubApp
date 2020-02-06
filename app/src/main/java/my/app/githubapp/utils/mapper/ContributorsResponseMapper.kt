package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.ContributorsResponse

object ContributorsResponseMapper {
    fun toGitHubContributorsList(contributorsResponse: ContributorsResponse) : List<GitHubContributor>{
        return contributorsResponse.gitHubContributor
    }
}