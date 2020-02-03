package my.app.githubapp.mvp.model.repositoryDetails

import io.reactivex.Observable
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.contract.RepositoryScreenContract.RepositoryDetailsInteractorInterface
import javax.inject.Inject

class RepositoryDetailsInteractor @Inject constructor(private  val mRepository : RepositoryDetailsRepository) : RepositoryDetailsInteractorInterface{


    override fun getRepoDetails(ownerLogin: String, repoName: String): Observable<GitHubRepo> {
        return mRepository.getGitHubRepo(ownerLogin,repoName)
    }

    override fun getRepoLanguages(ownerLogin: String, repoName: String) : Observable<List<LanguagePercentile>> {
        return mRepository.getRepoLanguages(ownerLogin,repoName)
    }

    override fun getOwnerInfo(ownerLogin: String) : Observable<GitHubUser>{
        return mRepository.getOwnerUserInfo(ownerLogin)
    }




}