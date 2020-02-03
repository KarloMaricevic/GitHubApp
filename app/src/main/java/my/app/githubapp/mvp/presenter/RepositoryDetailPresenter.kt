package my.app.githubapp.mvp.presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositoryScreenContract.*
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class RepositoryDetailPresenter @Inject constructor(private val mInteractor : RepositoryDetailsInteractorInterface,@Named("ownerName") private val mOwnerName : String,@Named("repoName") private val mRepoName : String) : RepositoryDetailsPresenterInterface {


    private var mView : RepositoryDetailsView? = null

    private val mCompositeDisposable = CompositeDisposable()

    private var areLanguagesExpanded : Boolean = false


    override fun subscribe(
        view: RepositoryDetailsView,
        state: RepositoryDetailsViewStateInterface?
    ) {
        mView = view
        initRepoDetails()

    }

    override fun subscribe(view: RepositoryDetailsView) = subscribe(view,null)

    override fun getState(): RepositoryDetailsViewStateInterface {
        TODO("not implemented")
    }


    override fun unsubscribe() {
        mView = null
        mCompositeDisposable.clear()
    }

    fun initRepoDetails() {
        val bindGitHubRepoDisposable =
         mInteractor
            .getRepoDetails(mOwnerName,mRepoName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mView?.bindRepoDetails(it)
                },
                {

                }
            )
        mInteractor.getOwnerInfo(mOwnerName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mView?.bindUserInfo(it)
                },
                {

                }

            )
        mCompositeDisposable.add(bindGitHubRepoDisposable)
    }

    private fun getRepositoryDetails(ownerName : String,repoName : String) : Observable<GitHubRepo> = mInteractor.getRepoDetails(ownerName,repoName)

    private fun getRepositoryLanguages(ownerName : String,repoName : String)  = mInteractor.getRepoLanguages(ownerName,repoName)


    override fun displayRepoLanguages() {
        areLanguagesExpanded = true
        val getRepoLanguagesDisposable = getRepositoryLanguages(mOwnerName,mRepoName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mView?.showRepoLanguages(it)
                },
                {

                }
            )
        mCompositeDisposable.add(getRepoLanguagesDisposable)

    }

    override fun hideRepoLanguages() {
        areLanguagesExpanded = false
        mView?.hideRepoLanguages()

    }

}