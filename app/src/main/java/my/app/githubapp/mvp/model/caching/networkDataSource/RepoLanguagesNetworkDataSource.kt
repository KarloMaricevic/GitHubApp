package my.app.githubapp.mvp.model.caching.networkDataSource

import io.reactivex.Single
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.domain.LanguagePercentage
import my.app.githubapp.mvp.model.caching.key.LanguageKey
import my.app.githubapp.mvp.model.retrofitService.RepositoryGitHubService
import my.app.githubapp.mvp.model.retrofitService.responses.LanguageResponse
import my.app.githubapp.utils.mapper.MapperInterface
import javax.inject.Inject

class RepoLanguagesNetworkDataSource @Inject constructor(
    private val mService: RepositoryGitHubService,
    private val mMapper: MapperInterface<LanguageResponse, List<LanguagePercentage>>
) : NetworkDataSourceInterface<LanguageKey, List<LanguagePercentage>> {

    override fun getData(key: LanguageKey): Single<List<LanguagePercentage>> {
        return mService.getRepositoryLanguage(key.ownerLogin, key.repoName)
            .map { mMapper.convert(it) }
    }
}
