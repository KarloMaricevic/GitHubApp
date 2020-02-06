package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.mvp.model.caching.key.ContributorsKey
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.RepositoryGitHubService
import my.app.githubapp.utils.mapper.ContributorsResponseMapper
import javax.inject.Inject

class ContributorsNetworkDataSource @Inject constructor(private val mService: RepositoryGitHubService) :  NetworkDataSourceInterface<ContributorsKey,List<GitHubContributor>> {
    override fun getData(key: ContributorsKey): Single<List<GitHubContributor>> {
        return mService.getRepositoryContributors(key.ownerName,key.repoName)
            .map { ContributorsResponseMapper.toGitHubContributorsList(it)}
        }
    }
