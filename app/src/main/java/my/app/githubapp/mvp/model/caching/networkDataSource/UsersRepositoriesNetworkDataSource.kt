package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.UsersRepositoriesKey
import my.app.githubapp.mvp.model.retrofitService.UserGitHubService
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoResponse
import my.app.githubapp.utils.mapper.BasicListMapper
import javax.inject.Inject

class UsersRepositoriesNetworkDataSource @Inject constructor(
    private val mUserGitHubService: UserGitHubService,
    private val mMapper: BasicListMapper<GitHubRepoResponse, GitHubRepo>
) :
    NetworkDataSourceInterface<UsersRepositoriesKey, List<GitHubRepo>> {
    override fun getData(key: UsersRepositoriesKey): Single<List<GitHubRepo>> {
        return mUserGitHubService
            .getUsersRepositories(key.usersLogin)
            .map { mMapper.convertList(it) }
    }
}
