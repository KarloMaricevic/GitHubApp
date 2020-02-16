package my.app.githubapp.mvp.model.repositoryDetails

import io.reactivex.Single
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.RepositoryDetailsInteractorInterface
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.RepositoryDetailsRepositoryInterface
import javax.inject.Inject

class RepositoryDetailsInteractor @Inject constructor(private  val mRepository : RepositoryDetailsRepositoryInterface) : RepositoryDetailsInteractorInterface{


    override fun getRepoDetails(ownerLogin: String, repoName: String): Single<GitHubRepo> {
        return mRepository.getGitHubRepo(ownerLogin,repoName)
    }

    override fun getRepoLanguages(ownerLogin: String, repoName: String) : Single<List<LanguagePercentile>> {
        return mRepository.getRepoLanguages(ownerLogin,repoName)
    }

    override fun getOwnerInfo(ownerLogin: String) : Single<GitHubUser>{
        return mRepository.getOwnerDetails(ownerLogin)
    }

    override fun getRepoContributors(ownerLogin: String, repoName: String): Single<List<GitHubContributor>> {
        return mRepository.getRepoContributors(ownerLogin,repoName)
    }


}