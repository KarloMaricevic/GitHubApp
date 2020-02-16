package my.app.githubapp.mvp.model.userDetails

import io.reactivex.Single
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.contract.UserDetailsContract.UserDetailsInteractorInterface
import javax.inject.Inject

@PerFragment
class UserDetailsInteractor @Inject constructor(
    private val mRepository : UserDetailsRepository
) : UserDetailsInteractorInterface{

    override fun getUserInfo(userLogin: String): Single<GitHubUser> {
        return mRepository.getGitHubUser(userLogin)
    }

    override fun getUserRepositories(userLogin: String): Single<List<GitHubRepo>> {
        return mRepository.getUsersRepositories(userLogin)
    }
}