package my.app.githubapp.mvp.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchInteractor
import my.app.githubapp.ui.*


class RepositorySearchPresenter(private val mRepositorySearchInteractor: RepositorySearchInteractor) : RepositorySearchContract.RepositorySearchPresenterInterface {

    private var mView: RepositorySearchContract.RepositorySearchView? = null
    private var mQuery: String = ""
    private var mSortBy: Int = 1

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
        return RepositorySearchViewState(mQuery)
    }

    override fun searchForRepos(query: String) {
        compositeDisposable.add(mRepositorySearchInteractor
            .getReposForQuery(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mQuery = query
                    mView?.showData(it)
                },
                {
                    Log.e("Error",it.message ?: "" )
                }
            )
        )
    }


    override fun unsubscribe() {
        mView = null
        compositeDisposable.clear()
    }

}