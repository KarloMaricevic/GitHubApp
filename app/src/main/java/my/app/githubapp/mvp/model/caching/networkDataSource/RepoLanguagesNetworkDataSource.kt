package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.model.caching.key.LanguageKey
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.RepositoryGitHubService
import my.app.githubapp.utils.mapper.LanguageResponseMapper
import javax.inject.Inject

class RepoLanguagesNetworkDataSource @Inject constructor(private val mService : RepositoryGitHubService) : NetworkDataSourceInterface<LanguageKey,List<LanguagePercentile>> {

    override fun getData(key: LanguageKey): Single<List<LanguagePercentile>> {
        return mService.getRepositoryLanguage(key.ownerLogin,key.repoName)
            .map { LanguageResponseMapper.toLanguagePercentileList(it) }
    }
}