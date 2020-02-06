package my.app.githubapp.mvp.presenter

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.*
import my.app.githubapp.ui.repositoryDetailsView.RepositoryDetailsViewState
import javax.inject.Inject

@PerFragment
class RepositoryDetailPresenter @Inject constructor(private val mInteractor: RepositoryDetailsInteractorInterface) :
    RepositoryDetailsPresenterInterface {


    private var mView: RepositoryDetailsView? = null

    private val mCompositeDisposable = CompositeDisposable()

    private lateinit var mOwnerName: String
    private lateinit var mRepoName: String
    private var areLanguagesExpanded: Boolean = false
    private var areContributorsExpanded: Boolean = false


    override fun subscribe(
        view: RepositoryDetailsView,
        state: RepositoryDetailsViewStateInterface?
    ) {
        mView = view
        initRepoDetails()
        if (state != null) {
            if (state.isRepoLanguagesVisible()) {
                presentRepoLanguages()
            }
            if (state.isContributorsShown()) {
                presentContributors()
            }
        }

    }

    override fun subscribe(view: RepositoryDetailsView) = subscribe(view, null)

    override fun getState(): RepositoryDetailsViewStateInterface {
        return RepositoryDetailsViewState(areLanguagesExpanded, areContributorsExpanded)
    }

    override fun unsubscribe() {
        mView = null
        mCompositeDisposable.clear()
    }

    override fun setLoginAndRepoName(ownerLogin: String, repoName: String) {
        mOwnerName = ownerLogin
        mRepoName = repoName
    }

    override fun presentRepoLanguages() {
        areLanguagesExpanded = true
        val getRepoLanguagesDisposable = getRepositoryLanguages(mOwnerName, mRepoName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mView?.showRepoLanguages(it)
                },
                {
                    mView?.loadingLanguagesError()
                }
            )
        mCompositeDisposable.add(getRepoLanguagesDisposable)
    }

    override fun hideRepoLanguages() {
        areLanguagesExpanded = false
        mView?.hideRepoLanguages()
    }

    override fun presentContributors() {
        areContributorsExpanded = true
        val getRepoContributorsDisposable =
            mInteractor.getRepoContributors(mOwnerName, mRepoName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mView?.showContributors(it)
                    },
                    {
                        mView?.loadingContributorsError()
                    }
                )
        mCompositeDisposable.add(getRepoContributorsDisposable)
    }

    override fun hideContributors() {
        mView?.hideContributors()
    }

    private fun initRepoDetails() {

        val repoDetailsDisposable =
            getRepositoryDetails(mOwnerName, mRepoName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mView?.bindRepoDetails(it)
                    },
                    {
                        mView?.fatalError()
                    }
                )

        val ownerDetailsDisposable =
            getOwnerDetails(mOwnerName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mView?.bindUserInfo(it)
                    },
                    {
                        mView?.fatalError()
                    }
                )
        mCompositeDisposable.addAll(repoDetailsDisposable, ownerDetailsDisposable)

    }

    private fun getRepositoryDetails(ownerName: String, repoName: String): Single<GitHubRepo> =
        mInteractor.getRepoDetails(ownerName, repoName)

    private fun getRepositoryLanguages(ownerName: String, repoName: String) =
        mInteractor.getRepoLanguages(ownerName, repoName)

    private fun getOwnerDetails(ownerName: String) = mInteractor.getOwnerInfo(ownerName)

}