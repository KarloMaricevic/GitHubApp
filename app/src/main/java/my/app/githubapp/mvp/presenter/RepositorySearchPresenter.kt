package my.app.githubapp.mvp.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchPresenterInterface
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchInteractor
import my.app.githubapp.ui.repositorySearchView.*
import javax.inject.Inject


@PerFragment
class RepositorySearchPresenter @Inject constructor(private val mRepositorySearchInteractor: RepositorySearchInteractorInterface) : RepositorySearchPresenterInterface {

    private var mView: RepositorySearchContract.RepositorySearchView? = null
    private var mQuery: String = ""
    private var mSortBy: Int = SORT_BY_REPO_NAME

    private val compositeDisposable = CompositeDisposable()



    override fun subscribe(view: RepositorySearchContract.RepositorySearchView) =
        subscribe(view, null)

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
        compositeDisposable.add(mRepositorySearchInteractor
            .getReposForQuery(query)
            .observeOn(AndroidSchedulers.mainThread())
            .map {sortList(it,mSortBy) }
            .subscribe(
                {
                    mView?.showData(it)
                },
                {
                    Log.e("Error",it.message ?: "" )
                }
            )
        )
    }

    override fun sortShowingRepos(sort : Int){
        compositeDisposable.add(mRepositorySearchInteractor
            .getReposForQuery(mQuery)
            .observeOn(AndroidSchedulers.mainThread())
            .map { sortList(it,sort) }
            .subscribe(
                {
                    mSortBy = sort
                    mView?.showData(it)
                },
                {
                    Log.e("Error",it.message ?: "" )
                })
        )
    }

    private fun sortList(gitHubRepoList : List<GitHubRepo>,sort : Int) : List<GitHubRepo>{
        return when(sort) {
            SORT_BY_REPO_NAME -> gitHubRepoList.sortedBy { it.name }
            SORT_BY_STAR -> gitHubRepoList.sortedByDescending  { it.staredNumber }
            SORT_BY_DATE -> gitHubRepoList.sortedByDescending  { it.createdAt }
            SORT_BY_FORKED ->  gitHubRepoList.sortedByDescending { it.forks }
            else -> throw NoSuchMethodException("Sort not implemented")
        }
    }

    override fun unsubscribe() {
        mView = null
        compositeDisposable.clear()
    }

}