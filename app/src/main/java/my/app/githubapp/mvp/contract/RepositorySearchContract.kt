package my.app.githubapp.mvp.contract

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
    }

}