package my.app.githubapp.di.dataSourceModule

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.model.caching.key.ContributorsKey
import my.app.githubapp.mvp.model.caching.key.LanguageKey
import my.app.githubapp.mvp.model.caching.key.RepoKey
import my.app.githubapp.mvp.model.caching.networkDataSource.ContributorsNetworkDataSource
import my.app.githubapp.mvp.model.caching.networkDataSource.GitHubRepositoryNetworkDataSource
import my.app.githubapp.mvp.model.caching.networkDataSource.RepoLanguagesNetworkDataSource
import javax.inject.Singleton

@Module
interface RepositoryGitHubSourceModule {

    companion object {

        @Provides
        @PerFragment
        fun providesGitHubRepoMemoryDataSource(networkSource: GitHubRepositoryNetworkDataSource): DataSource<RepoKey, GitHubRepo> {
            return DataSource.Builder<RepoKey, GitHubRepo>().setNetworkDataSource(networkSource)
                .build()
        }

        @Provides
        @PerFragment
        fun provides(networkDataSource: RepoLanguagesNetworkDataSource): DataSource<LanguageKey, List<LanguagePercentile>> {
            return DataSource.Builder<LanguageKey, List<LanguagePercentile>>()
                .setNetworkDataSource(networkDataSource).build()
        }


        @Provides
        @PerFragment
        fun providesContributorsMemoryDataSource(networkSource: ContributorsNetworkDataSource): DataSource<ContributorsKey, List<GitHubContributor>> {
            return DataSource.Builder<ContributorsKey, List<GitHubContributor>>()
                .setNetworkDataSource(networkSource).build()
        }
    }
}