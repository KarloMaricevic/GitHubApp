package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.caching.key.GitHubUserKey
import my.app.githubapp.mvp.model.retrofitService.UserGitHubService
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubUserResponse
import my.app.githubapp.utils.mapper.MapperInterface
import javax.inject.Inject


class GitHubUserNetworkDataSource @Inject constructor(
    private val mService: UserGitHubService,
    private val mMapper: MapperInterface<GitHubUserResponse, GitHubUser>
) :
    NetworkDataSourceInterface<GitHubUserKey, GitHubUser> {
    override fun getData(key: GitHubUserKey): Single<GitHubUser> {
        return mService.getUserInfo(key.userLogin)
            .map { mMapper.convert(it) }
    }
}