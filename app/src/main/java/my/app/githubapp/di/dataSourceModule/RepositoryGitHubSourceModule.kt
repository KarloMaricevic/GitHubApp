package my.app.githubapp.di.dataSourceModule

import dagger.Module
import dagger.Provides
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.LanguagePercentage
import my.app.githubapp.mvp.model.caching.key.ContributorsKey
import my.app.githubapp.mvp.model.caching.key.LanguageKey
import my.app.githubapp.mvp.model.caching.key.RepoKey
import my.app.githubapp.mvp.model.caching.networkDataSource.ContributorsNetworkDataSource
import my.app.githubapp.mvp.model.caching.networkDataSource.GitHubRepositoryNetworkDataSource
import my.app.githubapp.mvp.model.caching.networkDataSource.RepoLanguagesNetworkDataSource

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
        fun provides(networkDataSource: RepoLanguagesNetworkDataSource): DataSource<LanguageKey, List<LanguagePercentage>> {
            return DataSource.Builder<LanguageKey, List<LanguagePercentage>>()
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