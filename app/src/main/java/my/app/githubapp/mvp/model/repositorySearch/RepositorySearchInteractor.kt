package my.app.githubapp.mvp.model.repositorySearch

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.GitHubApiQueryResponse

class RepositorySearchInteractor(private val mRepositorySearchRepository: RepositorySearchRepository) {


    fun getReposForQuery(query : String) : Observable<List<GitHubRepo>>{
        return mRepositorySearchRepository.getGitHubRepositories(query).subscribeOn(Schedulers.io())
    }

}