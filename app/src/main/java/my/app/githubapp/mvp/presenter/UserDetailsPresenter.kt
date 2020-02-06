package my.app.githubapp.mvp.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.processNextEventInCurrentThread
import my.app.githubapp.mvp.contract.UserDetailsContract.*
import my.app.githubapp.ui.userDetailsView.UserDetailsViewState
import javax.inject.Inject

class UserDetailsPresenter @Inject constructor(private val mInteractor : UserDetailsInteractorInterface) : UserDetailsPresenterInterface{

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
            .observeOn(AndroidSchedulers.mainThread())
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
            .observeOn(AndroidSchedulers.mainThread())
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