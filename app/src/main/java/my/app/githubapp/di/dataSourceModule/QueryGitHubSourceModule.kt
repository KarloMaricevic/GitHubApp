package my.app.githubapp.di.dataSourceModule

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.cacher.NetworkDataSourceInterface
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.caching.key.QueryKey
import my.app.githubapp.mvp.model.caching.networkDataSource.RepositorySearchNetworkDataSource
import javax.inject.Singleton

@Module
interface QueryGitHubSourceModule {


    companion object {
        @Provides
        @PerFragment
        fun providesNetworkDataSource(networkSource: RepositorySearchNetworkDataSource): DataSource<QueryKey, List<GitHubRepo>> {
            return DataSource.Builder<QueryKey,List<GitHubRepo>>().setNetworkDataSource(networkSource).build()
        }
    }
}