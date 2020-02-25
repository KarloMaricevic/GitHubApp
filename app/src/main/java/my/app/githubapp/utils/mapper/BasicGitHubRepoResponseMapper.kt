package my.app.githubapp.utils.mapper

import my.app.githubapp.domain.BasicGitHubRepo
import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.mvp.model.retrofitService.responses.BasicGitHubRepoResponse

object BasicGitHubRepoResponseMapper : MapperInterface<BasicGitHubRepoResponse,BasicGitHubRepo> {
    override fun convert(objectToConvert: BasicGitHubRepoResponse): BasicGitHubRepo {
        return BasicGitHubRepo(
            objectToConvert.name,
            objectToConvert.id,
            BasicGitHubUserResponseMapper.convert(objectToConvert.owner)
        )
    }
}