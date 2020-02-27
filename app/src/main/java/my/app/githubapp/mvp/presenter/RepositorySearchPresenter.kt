package my.app.githubapp.mvp.presenter

import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchPresenterAbstraction
import my.app.githubapp.ui.repositorySearchView.RepositorySearchViewState
import my.app.githubapp.ui.repositorySearchView.SORT_BY_REPO_NAME
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import my.app.githubapp.utils.sorter.SorterInterface
import javax.inject.Inject

@PerFragment
class RepositorySearchPresenter @Inject constructor(
    private val mRepositorySearchInteractor: RepositorySearchInteractorInterface,
    private val mSchedulersProvider: SchedulersProviderInterface,
    private val mGitHubRepoSorter : SorterInterface<GitHubRepo>
) :
    RepositorySearchPresenterAbstraction() {

    private var mQuery: String = ""
    private var mSortBy: Int = SORT_BY_REPO_NAME


    override fun subscribe(
        view: RepositorySearchContract.RepositorySearchView,
        state: RepositorySearchContract.RepositorySearchViewStateInterface?
    ) {
        mView = view
        if (state != null) {
            mQuery = state.getQuery()
            mSortBy = state.getSortBy()
            searchForRepos(mQuery)
        }
    }


    override fun getState(): RepositorySearchContract.RepositorySearchViewStateInterface {
        return RepositorySearchViewState(
            mQuery,
            mSortBy
        )
    }

    override fun searchForRepos(query: String) {
        mQuery = query
        mCompositeDisposable.add(mRepositorySearchInteractor
            .getReposForQuery(query)
            .observeOn(mSchedulersProvider.getMainThread())
            .map { mGitHubRepoSorter.sortIterable(it, mSortBy).toList() }
            .subscribe(
                {
                    mView?.showData(it)
                },
                {
                    mView?.queryError()
                }
            )
        )
    }

    override fun sortShowingRepos(sort: Int) {
        mSortBy = sort
        mCompositeDisposable.add(mRepositorySearchInteractor
            .getReposForQuery(mQuery)
            .observeOn(mSchedulersProvider.getMainThread())
            .map { mGitHubRepoSorter.sortIterable(it, sort).toList() }
            .subscribe(
                {
                    mView?.showData(it)
                },
                {
                    mView?.queryError()
                })
        )
    }

    override fun tryQueryAgain() {
        searchForRepos(mQuery)
    }
}