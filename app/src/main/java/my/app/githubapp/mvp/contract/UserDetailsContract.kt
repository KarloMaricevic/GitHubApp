package my.app.githubapp.mvp.contract

import io.reactivex.Single
import my.app.githubapp.core.BaseInteractor
import my.app.githubapp.core.BaseStatefulPresenter
import my.app.githubapp.core.BaseView
import my.app.githubapp.core.State
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser

interface UserDetailsContract {

    interface UserDetailsViewStateInterface : State {
        fun isUserRepositoriesShown(): Boolean
    }

    interface UserDetailsView : BaseView {
        fun showUserInfo(gitHubUser: GitHubUser)
        fun showUsersRepositories(gitHubRepoList: List<GitHubRepo>)
        fun hideUsersRepositories()
        fun showErrorLoadingRepositories()
    }

    abstract class UserDetailsPresenterAbstraction :
        BaseStatefulPresenter<UserDetailsView, UserDetailsViewStateInterface>() {
        abstract fun presentUserInfo()
        abstract fun presentUserRepositories()
        abstract fun hideUserRepositories()
        abstract fun setUserLogin(userLogin: String)
    }

    interface UserDetailsInteractorInterface : BaseInteractor {
        fun getUserInfo(userLogin: String): Single<GitHubUser>
        fun getUserRepositories(userLogin: String): Single<List<GitHubRepo>>
    }

    interface UserDetailsRepositoryInterface {
        fun getGitHubUser(userLogin: String): Single<GitHubUser>
        fun getUsersRepositories(userLogin: String): Single<List<GitHubRepo>>
    }
}
