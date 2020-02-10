package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.QueryKey
import my.app.githubapp.mvp.model.retrofitService.queryGitHubService.QueryGitHubService
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.RepositoryGitHubService
import javax.inject.Inject

class RepositorySearchNetworkDataSource @Inject constructor(private val mQueryService : QueryGitHubService, private val mRepoService : RepositoryGitHubService) : NetworkDataSourceInterface<QueryKey,List<GitHubRepo>> {
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
                                basicGitHubRepo.name)
                            .toObservable()
                    observableList.add(gitHubRepoObservable)
                }

                return@flatMap observableList
                    .combineLatest { it }
                    .firstOrError()
            }

    }


    //private val service : RepositorySearchGitHubService
        /*return service.getReposWithQueryInName(key.query)
            .flatMap { gitHubRepoResponse ->
                if (gitHubRepoResponse.totalCountOfRepositories == 0) {
                    return@flatMap Single.just(listOf<GitHubRepo>())
                }

                val singleList = ArrayList<Observable<HashMap<String, Int>>>()
                for (gitHubRepo in gitHubRepoResponse.responseItems) {
                    singleList.add(
                        getRepoWatchersNumber(
                            gitHubRepo.owner.login,
                            gitHubRepo.name,
                            gitHubRepo.id
                        )
                    )
                }

                return@flatMap singleList
                    .combineLatest { it }
                    .firstOrError()
                    .map<List<GitHubRepo>> {
                        val gitHubRepoList = ArrayList<GitHubRepo>()
                        for (hashMap in it) {
                            for (gitHubRepo in gitHubRepoResponse.responseItems) {
                                if (hashMap["RepoId"] == gitHubRepo.id) {
                                    //gitHubRepoList.add(gitHubRepo.copy(watcherNumber = hashMap["NumberOfWatchers"]!!))
                                    break
                                }
                            }
                        }
                        return@map gitHubRepoList
                    }
            }
    }

    private fun getRepoWatchersNumber(
        ownerName: String,
        repoName: String,
        repoId: Int
    ): Observable<HashMap<String, Int>> {
        return service.getRepoWatchers(ownerName, repoName).toObservable()
            .map {
                val hashMap = HashMap<String, Int>()
                hashMap["RepoId"] = repoId
                hashMap["NumberOfWatchers"] = it.count()
                return@map hashMap
            }
    }*/
}