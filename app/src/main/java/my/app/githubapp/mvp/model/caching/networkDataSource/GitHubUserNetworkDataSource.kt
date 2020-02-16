package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.caching.key.GitHubUserKey
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.UserGitHubService
import javax.inject.Inject


class GitHubUserNetworkDataSource  @Inject constructor(private val mService : UserGitHubService) : NetworkDataSourceInterface<GitHubUserKey,GitHubUser> {
    override fun getData(key: GitHubUserKey): Single<GitHubUser> {
        return mService.getUserInfo(key.userLogin)
    }
}