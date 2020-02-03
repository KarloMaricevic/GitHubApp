package my.app.githubapp.mvp.contract

import io.reactivex.Observable
import my.app.githubapp.core.BaseInteractor
import my.app.githubapp.core.BaseStatefulPresenter
import my.app.githubapp.core.BaseView
import my.app.githubapp.core.State
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile

interface RepositoryScreenContract {

    interface RepositoryDetailsView : BaseView{
        fun bindRepoDetails(gitHubRepo: GitHubRepo)
        fun bindUserInfo(owner: GitHubUser)
        fun showRepoLanguages(languagesPercentileList : List<LanguagePercentile>)
        fun hideRepoLanguages()
    }

    interface RepositoryDetailsViewStateInterface : State{
        fun isRepoLanguagesVisible() : Boolean
        fun isContributorsShown() : Boolean

    }


    interface RepositoryDetailsPresenterInterface : BaseStatefulPresenter<RepositoryDetailsView,RepositoryDetailsViewStateInterface>{
        fun displayRepoLanguages()
        fun hideRepoLanguages()
    }


    interface RepositoryDetailsInteractorInterface : BaseInteractor{
        fun getRepoDetails(ownerLogin: String,repoName: String) : Observable<GitHubRepo>
        fun getRepoLanguages(ownerLogin: String,repoName: String): Observable<List<LanguagePercentile>>
        fun getOwnerInfo(ownerLogin: String): Observable<GitHubUser>
    }



}