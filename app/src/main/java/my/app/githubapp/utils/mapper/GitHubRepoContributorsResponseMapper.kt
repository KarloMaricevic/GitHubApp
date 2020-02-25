package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoContributorResponse

object GitHubRepoContributorsResponseMapper :
    BasicListMapper<GitHubRepoContributorResponse, GitHubContributor>() {

    override fun convert(objectToConvert: GitHubRepoContributorResponse): GitHubContributor {
        return GitHubContributor(
            objectToConvert.login,
            objectToConvert.id,
            objectToConvert.avatarUrl,
            objectToConvert.contributions
        )
    }
}