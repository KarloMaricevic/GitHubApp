package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.mvp.model.caching.key.ContributorsKey
import my.app.githubapp.mvp.model.retrofitService.RepositoryGitHubService
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoContributorResponse
import my.app.githubapp.utils.mapper.BasicListMapper
import javax.inject.Inject

class ContributorsNetworkDataSource @Inject constructor(
    private val mService: RepositoryGitHubService,
    private val mMapper: BasicListMapper<GitHubRepoContributorResponse, GitHubContributor>
) :
    NetworkDataSourceInterface<ContributorsKey, List<GitHubContributor>> {
    override fun getData(key: ContributorsKey): Single<List<GitHubContributor>> {
        return mService.getRepositoryContributors(key.ownerName, key.repoName)
            .map {
                mMapper.convertList(it)
            }
    }
}
