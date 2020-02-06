package my.app.githubapp.mvp.model.repositorySearch

import io.reactivex.Observable
import io.reactivex.Single
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchRepositoryInterface
import javax.inject.Inject


@PerFragment
class RepositorySearchInteractor @Inject constructor(private val mRepositorySearchRepository: RepositorySearchRepositoryInterface) : RepositorySearchInteractorInterface {

    override fun getReposForQuery(query : String) : Single<List<GitHubRepo>>{
        return mRepositorySearchRepository.getReposFromQuery(query)
    }
}