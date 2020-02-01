package my.app.githubapp.mvp.model.repositorySearch

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.model.GitHubApiQueryResponse
import javax.inject.Inject


@PerFragment
class RepositorySearchInteractor @Inject constructor(private val mRepositorySearchRepository: RepositorySearchRepository) : RepositorySearchInteractorInterface {


    override fun getReposForQuery(query : String) : Observable<List<GitHubRepo>>{
        return mRepositorySearchRepository.getGitHubRepositories(query)
    }

}