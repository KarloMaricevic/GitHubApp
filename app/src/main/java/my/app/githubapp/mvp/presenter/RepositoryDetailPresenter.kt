package my.app.githubapp.mvp.presenter

import io.reactivex.Single
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositoryDetailsContract
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.RepositoryDetailsView
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.RepositoryDetailsViewStateInterface
import my.app.githubapp.ui.repositoryDetailsView.RepositoryDetailsViewState
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import javax.inject.Inject

@PerFragment
@Suppress("TooManyFunctions")
class RepositoryDetailPresenter @Inject constructor(
    private val mInteractor: RepositoryDetailsContract.RepositoryDetailsInteractorInterface,
    private val mSchedulersProvider: SchedulersProviderInterface
) :
    RepositoryDetailsContract.RepositoryDetailsPresenterAbstraction() {

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

    override fun getState(): RepositoryDetailsViewStateInterface {
        return RepositoryDetailsViewState(areLanguagesExpanded, areContributorsExpanded)
    }

    override fun setLoginAndRepoName(ownerLogin: String, repoName: String) {
        mOwnerName = ownerLogin
        mRepoName = repoName
    }

    override fun presentRepoLanguages() {
        areLanguagesExpanded = true
        val getRepoLanguagesDisposable = getRepositoryLanguages(mOwnerName, mRepoName)
            .observeOn(mSchedulersProvider.getMainThread())
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
                .observeOn(mSchedulersProvider.getMainThread())
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
        areContributorsExpanded = false
        mView?.hideContributors()
    }

    private fun initRepoDetails() {

        val repoDetailsDisposable =
            getRepositoryDetails(mOwnerName, mRepoName)
                .observeOn(mSchedulersProvider.getMainThread())
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
                .observeOn(mSchedulersProvider.getMainThread())
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
