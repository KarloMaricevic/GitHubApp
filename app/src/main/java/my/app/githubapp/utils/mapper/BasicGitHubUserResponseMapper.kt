package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.mvp.model.retrofitService.responses.BasicGitHubUserResponse

object BasicGitHubUserResponseMapper : MapperInterface<BasicGitHubUserResponse, BasicGitHubUser> {
    override fun convert(objectToConvert: BasicGitHubUserResponse): BasicGitHubUser {
        return BasicGitHubUser(
            objectToConvert.login,
            objectToConvert.id,
            objectToConvert.avatarUrl
        )
    }
}