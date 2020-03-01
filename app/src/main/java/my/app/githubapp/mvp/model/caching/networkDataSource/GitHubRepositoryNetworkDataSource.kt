package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.RepoKey
import my.app.githubapp.mvp.model.retrofitService.RepositoryGitHubService
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoResponse
import my.app.githubapp.utils.mapper.BasicListMapper
import javax.inject.Inject

class GitHubRepositoryNetworkDataSource @Inject constructor(
    private val mService: RepositoryGitHubService,
    private val mMapper: BasicListMapper<GitHubRepoResponse, GitHubRepo>
) : NetworkDataSourceInterface<RepoKey, GitHubRepo> {
    override fun getData(key: RepoKey): Single<GitHubRepo> {
        return mService.getRepositoryInformation(key.ownerLogin, key.repoName)
            .map { mMapper.convert(it) }
    }
}
