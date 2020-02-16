package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.UsersRepositoriesKey
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.UserGitHubService
import javax.inject.Inject

class UsersRepositoriesNetworkDataSource @Inject constructor(private val mUserGitHubService: UserGitHubService) : NetworkDataSourceInterface<UsersRepositoriesKey,List<GitHubRepo>> {
    override fun getData(key: UsersRepositoriesKey): Single<List<GitHubRepo>> {
        return mUserGitHubService
            .getUsersRepositories(key.usersLogin) }
}