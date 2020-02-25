package my.app.githubapp.mvp.contract

import io.reactivex.Single
import my.app.githubapp.core.BaseInteractor
import my.app.githubapp.core.BaseStatefulPresenter
import my.app.githubapp.core.BaseView
import my.app.githubapp.core.State
import my.app.githubapp.domain.GitHubRepo

interface RepositorySearchContract {

    interface RepositorySearchView : BaseView {
        fun showData(data : List<GitHubRepo>)
        fun queryError()
    }


    interface RepositorySearchViewStateInterface : State {
        fun getQuery() : String
        fun getSortBy() : Int
    }

    abstract class RepositorySearchPresenterAbstraction : BaseStatefulPresenter<RepositorySearchView, RepositorySearchViewStateInterface>() {
        abstract fun searchForRepos(query : String)
        abstract fun sortShowingRepos(sort : Int)
        abstract fun tryQueryAgain()
    }

    interface RepositorySearchInteractorInterface : BaseInteractor{
        fun getReposForQuery(query : String) : Single<List<GitHubRepo>>
    }

    interface RepositorySearchRepositoryInterface{
        fun getReposFromQuery(query : String) : Single<List<GitHubRepo>>
    }

}