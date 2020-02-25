package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.caching.key.GitHubUserKey
import my.app.githubapp.mvp.model.retrofitService.UserGitHubService
import my.app.githubapp.utils.mapper.GitHubUserResponseMapper
import javax.inject.Inject


class GitHubUserNetworkDataSource @Inject constructor(private val mService: UserGitHubService) :
    NetworkDataSourceInterface<GitHubUserKey, GitHubUser> {
    override fun getData(key: GitHubUserKey): Single<GitHubUser> {
        return mService.getUserInfo(key.userLogin)
            .map { GitHubUserResponseMapper.convert(it) }
    }
}