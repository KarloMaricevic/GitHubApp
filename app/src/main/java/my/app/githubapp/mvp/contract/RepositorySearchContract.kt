package my.app.githubapp.mvp.contract

import io.reactivex.Observable
import my.app.githubapp.core.BaseInteractor
import my.app.githubapp.core.BaseStatefulPresenter
import my.app.githubapp.core.BaseView
import my.app.githubapp.core.State
import my.app.githubapp.domain.GitHubRepo

interface RepositorySearchContract {

    interface RepositorySearchView : BaseView {
        fun showData(data : List<GitHubRepo>)
    }


    interface RepositorySearchViewStateInterface : State {
        fun getQuery() : String
        fun getSortBy() : Int
    }

    interface RepositorySearchPresenterInterface : BaseStatefulPresenter<RepositorySearchView,RepositorySearchViewStateInterface>{
        fun searchForRepos(query : String)
        fun sortShowingRepos(sort : Int)
    }

    interface RepositorySearchInteractorInterface : BaseInteractor{
        fun getReposForQuery(query : String) : Observable<List<GitHubRepo>>
    }

}