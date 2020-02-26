package my.app.githubapp.mvp.presenter

import my.app.githubapp.mvp.contract.UserDetailsContract.*
import my.app.githubapp.ui.userDetailsView.UserDetailsViewState
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import javax.inject.Inject

class UserDetailsPresenter @Inject constructor(
    private val mInteractor: UserDetailsInteractorInterface,
    private val mSchedulersProvider: SchedulersProviderInterface
) : UserDetailsPresenterAbstraction() {

    private var isUserRepositoriesButtonActivated: Boolean = false

    private lateinit var mUserLogin: String

    override fun subscribe(
        view: UserDetailsView,
        state: UserDetailsViewStateInterface?
    ) {
        mView = view

        val gitHubUserDisposable = getUserInfo(mUserLogin)
            .observeOn(mSchedulersProvider.getMainThread())
            .subscribe(
                {
                    mView?.showUserInfo(it)
                },
                {
                    mView?.fatalError()
                }
            )

        if (state != null) {
            if (state.isUserRepositoriesShown()) {
                presentUserRepositories()
            }
        }

        mCompositeDisposable.add(gitHubUserDisposable)
    }

    override fun getState(): UserDetailsViewStateInterface =
        UserDetailsViewState(isUserRepositoriesButtonActivated)

    override fun presentUserInfo() {
        getUserInfo(mUserLogin)
    }

    override fun presentUserRepositories() {
        isUserRepositoriesButtonActivated = true
        val getUsersRepoDisposable = getUsersRepositories(mUserLogin)
            .observeOn(mSchedulersProvider.getMainThread())
            .subscribe(
                {
                    mView?.showUsersRepositories(it)
                },
                {
                    isUserRepositoriesButtonActivated = false
                    mView?.showErrorLoadingRepositories()
                }
            )

        mCompositeDisposable.add(getUsersRepoDisposable)
    }

    override fun hideUserRepositories() {
        isUserRepositoriesButtonActivated = false
        mView?.hideUsersRepositories()
    }

    override fun setUserLogin(userLogin: String) {
        mUserLogin = userLogin
    }

    private fun getUserInfo(userLogin: String) = mInteractor.getUserInfo(userLogin)

    private fun getUsersRepositories(userLogin: String) = mInteractor.getUserRepositories(userLogin)

}