package my.app.githubapp.mvp.presenter

import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchPresenterAbstraction
import my.app.githubapp.ui.repositorySearchView.*
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import javax.inject.Inject

@PerFragment
class RepositorySearchPresenter @Inject constructor(
    private val mRepositorySearchInteractor: RepositorySearchInteractorInterface,
    private val mSchedulersProvider: SchedulersProviderInterface
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
            .map { sortList(it, mSortBy) }
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
        mCompositeDisposable.add(mRepositorySearchInteractor
            .getReposForQuery(mQuery)
            .observeOn(mSchedulersProvider.getMainThread())
            .map { sortList(it, sort) }
            .subscribe(
                {
                    mSortBy = sort
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

    private fun sortList(gitHubRepoList: List<GitHubRepo>, sort: Int): List<GitHubRepo> {
        return when (sort) {
            SORT_BY_REPO_NAME -> gitHubRepoList.sortedBy { it.name }
            SORT_BY_STAR -> gitHubRepoList.sortedByDescending { it.staredNumber }
            SORT_BY_DATE -> gitHubRepoList.sortedByDescending { it.createdAt }
            SORT_BY_FORKED -> gitHubRepoList.sortedByDescending { it.forks }
            else -> throw NoSuchMethodException("Sort not implemented")
        }
    }
}