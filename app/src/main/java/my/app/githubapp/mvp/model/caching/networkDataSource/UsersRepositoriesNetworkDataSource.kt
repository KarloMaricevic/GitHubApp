package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.UsersRepositoriesKey
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.UserGitHubService
import my.app.githubapp.utils.mapper.GitHubUsersRepositoriesMapper
import javax.inject.Inject

class UsersRepositoriesNetworkDataSource @Inject constructor(private val mUserGitHubService: UserGitHubService) : NetworkDataSourceInterface<UsersRepositoriesKey,List<GitHubRepo>> {
    override fun getData(key: UsersRepositoriesKey): Single<List<GitHubRepo>> {
        return mUserGitHubService
            .getUsersRepositories(key.usersLogin)
            .subscribeOn(Schedulers.io())
            .map { GitHubUsersRepositoriesMapper.toGitHubRepositoriesList(it) }
    }
}