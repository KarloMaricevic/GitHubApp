package my.app.githubapp.mvp.model.userDetails

import io.reactivex.Single
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.contract.UserDetailsContract.UserDetailsRepositoryInterface
import my.app.githubapp.mvp.model.caching.key.GitHubUserKey
import my.app.githubapp.mvp.model.caching.key.UsersRepositoriesKey
import javax.inject.Inject

@PerFragment
class UserDetailsRepository @Inject constructor(
    private val mGitHubUserDataSource: DataSource<GitHubUserKey, GitHubUser>,
    private val mUsersRepositoriesDataSource: DataSource<UsersRepositoriesKey, List<GitHubRepo>>

) : UserDetailsRepositoryInterface {
    override fun getGitHubUser(userLogin: String): Single<GitHubUser> {
        val key = GitHubUserKey(userLogin)
        return mGitHubUserDataSource.getFromMemory(key)
    }

    override fun getUsersRepositories(userLogin: String): Single<List<GitHubRepo>> {
        val key = UsersRepositoriesKey(userLogin)
        return mUsersRepositoriesDataSource.getFromMemory(key)
    }
}