package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoResponse

object GitHubRepoResponseMapper : BasicListMapper<GitHubRepoResponse, GitHubRepo>() {
    override fun convert(objectToConvert: GitHubRepoResponse): GitHubRepo {
        return GitHubRepo(
            objectToConvert.name,
            objectToConvert.id,
            BasicGitHubUserResponseMapper.convert(objectToConvert.owner),
            objectToConvert.fullName,
            objectToConvert.htmlUrl,
            objectToConvert.watchersCount,
            objectToConvert.forksCount,
            objectToConvert.openIssuesCount,
            objectToConvert.stargazersCount,
            objectToConvert.createdAt,
            objectToConvert.language
        )
    }
}