package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubUserResponse

object GitHubUserResponseMapper : MapperInterface<GitHubUserResponse, GitHubUser> {
    override fun convert(objectToConvert: GitHubUserResponse): GitHubUser {
        return GitHubUser(
            objectToConvert.id,
            objectToConvert.login,
            objectToConvert.avatarUrl,
            objectToConvert.htmlUrl,
            objectToConvert.name,
            objectToConvert.company,
            objectToConvert.blog,
            objectToConvert.location,
            objectToConvert.email,
            objectToConvert.bio,
            objectToConvert.publicRepos,
            objectToConvert.followers,
            objectToConvert.following,
            objectToConvert.createdAt,
            objectToConvert.updatedAt
        )
    }
}