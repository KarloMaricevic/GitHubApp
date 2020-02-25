package my.app.githubapp.mvp.contract

import io.reactivex.Single
import my.app.githubapp.core.BaseInteractor
import my.app.githubapp.core.BaseStatefulPresenter
import my.app.githubapp.core.BaseView
import my.app.githubapp.core.State
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentage

interface RepositoryDetailsContract {

    interface RepositoryDetailsView : BaseView {
        fun bindRepoDetails(gitHubRepo: GitHubRepo)
        fun bindUserInfo(owner: GitHubUser)
        fun showRepoLanguages(languagesPercentileList: List<LanguagePercentage>)
        fun hideRepoLanguages()
        fun showContributors(contributorsList: List<GitHubContributor>)
        fun hideContributors()
        fun loadingContributorsError()
        fun loadingLanguagesError()
    }

    interface RepositoryDetailsViewStateInterface : State {
        fun isRepoLanguagesVisible(): Boolean
        fun isContributorsShown(): Boolean

    }


    abstract class RepositoryDetailsPresenterAbstraction :
        BaseStatefulPresenter<RepositoryDetailsView, RepositoryDetailsViewStateInterface>() {
        abstract fun setLoginAndRepoName(ownerLogin: String, repoName: String)
        abstract fun presentRepoLanguages()
        abstract fun hideRepoLanguages()
        abstract fun presentContributors()
        abstract fun hideContributors()
    }


    interface RepositoryDetailsInteractorInterface : BaseInteractor {
        fun getRepoDetails(ownerLogin: String, repoName: String): Single<GitHubRepo>
        fun getRepoLanguages(ownerLogin: String, repoName: String): Single<List<LanguagePercentage>>
        fun getOwnerInfo(ownerLogin: String): Single<GitHubUser>
        fun getRepoContributors(
            ownerLogin: String,
            repoName: String
        ): Single<List<GitHubContributor>>
    }

    interface RepositoryDetailsRepositoryInterface {
        fun getGitHubRepo(ownerLogin: String, repoName: String): Single<GitHubRepo>
        fun getRepoContributors(
            ownerLogin: String,
            repoName: String
        ): Single<List<GitHubContributor>>

        fun getRepoLanguages(ownerLogin: String, repoName: String): Single<List<LanguagePercentage>>
        fun getOwnerDetails(ownerLogin: String): Single<GitHubUser>
    }

}