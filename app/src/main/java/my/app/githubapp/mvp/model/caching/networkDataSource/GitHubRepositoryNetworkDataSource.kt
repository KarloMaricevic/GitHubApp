package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.RepoKey
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.RepositoryGitHubService
import my.app.githubapp.utils.mapper.GitHubResponseMapper
import javax.inject.Inject
import javax.inject.Singleton

class GitHubRepositoryNetworkDataSource @Inject constructor(private val mService : RepositoryGitHubService) : NetworkDataSourceInterface<RepoKey,GitHubRepo> {
    override fun getData(key: RepoKey): Single<GitHubRepo> {
        return mService.getRepositoryInformation(key.ownerLogin,key.repoName).map {
            GitHubResponseMapper.toGitHubRepo(it)
        }
    }
}