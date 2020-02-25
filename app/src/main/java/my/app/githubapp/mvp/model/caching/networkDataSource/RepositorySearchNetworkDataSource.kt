package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.QueryKey
import my.app.githubapp.mvp.model.retrofitService.QueryGitHubService
import my.app.githubapp.mvp.model.retrofitService.RepositoryGitHubService
import my.app.githubapp.utils.mapper.GitHubRepoResponseMapper
import javax.inject.Inject

class RepositorySearchNetworkDataSource @Inject constructor(
    private val mQueryService: QueryGitHubService,
    private val mRepoService: RepositoryGitHubService
) : NetworkDataSourceInterface<QueryKey, List<GitHubRepo>> {
    override fun getData(key: QueryKey): Single<List<GitHubRepo>> {

        return mQueryService
            .getReposWithQueryInName(key.query)
            .subscribeOn(Schedulers.io())
            .map { it.responseItems }
            .flatMap { basicGitHubRepoList ->
                val observableList = arrayListOf<Observable<GitHubRepo>>()
                for (basicGitHubRepo in basicGitHubRepoList) {
                    val gitHubRepoObservable =
                        mRepoService
                            .getRepositoryInformation(
                                basicGitHubRepo.owner.login,
                                basicGitHubRepo.name
                            )
                            .toObservable()
                    observableList.add(gitHubRepoObservable.map {
                        GitHubRepoResponseMapper.convert(it)
                    })
                }

                return@flatMap observableList
                    .combineLatest { it }
                    .firstOrError()
            }

    }
}