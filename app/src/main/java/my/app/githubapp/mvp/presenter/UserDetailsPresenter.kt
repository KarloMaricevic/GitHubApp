package my.app.githubapp.mvp.presenter

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import my.app.githubapp.mvp.contract.UserDetailsContract.*
import my.app.githubapp.ui.userDetailsView.UserDetailsViewState
import javax.inject.Inject
import javax.inject.Named

class UserDetailsPresenter @Inject constructor(private val mInteractor : UserDetailsInteractorInterface,@Named("MainThread") private val mMainThread : Scheduler) : UserDetailsPresenterInterface{

    private var mView : UserDetailsView? = null

    private var isUserRepositoriesButtonActivated : Boolean = false

    private lateinit var mUserLogin : String

    private val mCompositeDisposable = CompositeDisposable()

    override fun subscribe(
        view: UserDetailsView,
        state: UserDetailsViewStateInterface?
    ) {
        mView = view

        val gitHubUserDisposable = getUserInfo(mUserLogin)
            .observeOn(mMainThread)
            .subscribe(
                {
                    mView?.showUserInfo(it)
                },
                {
                    mView?.fatalError()
                }
            )

        if(state != null){
            if(state.isUserRepositoriesShown()){
                presentUserRepositories()
            }
        }

        mCompositeDisposable.add(gitHubUserDisposable)
    }

    override fun subscribe(view: UserDetailsView) = subscribe(view,null)

    override fun getState(): UserDetailsViewStateInterface = UserDetailsViewState(isUserRepositoriesButtonActivated)

    override fun presentUserInfo() {
        getUserInfo(mUserLogin)
    }

    override fun presentUserRepositories() {
        isUserRepositoriesButtonActivated = true
        val getUsersRepoDisposable = getUsersRepositories(mUserLogin)
            .observeOn(mMainThread)
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

    override fun unsubscribe() {
        mCompositeDisposable.clear()
        mView = null
    }

    private fun getUserInfo(userLogin : String) = mInteractor.getUserInfo(userLogin)

    private fun getUsersRepositories(userLogin: String) = mInteractor.getUserRepositories(userLogin)

}