package my.app.githubapp.mvp.model.repositorySearch

import io.reactivex.Single
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchRepositoryInterface
import my.app.githubapp.mvp.model.caching.key.QueryKey
import javax.inject.Inject

@PerFragment
class RepositorySearchRepository @Inject constructor(private val dataSource: DataSource<QueryKey, List<GitHubRepo>>) :
    RepositorySearchRepositoryInterface {

    override fun getReposFromQuery(query: String): Single<List<GitHubRepo>> {
        val key = QueryKey(query)
        return dataSource.getFromMemory(key)
    }
}
