package my.app.githubapp.di.dataSourceModule

import dagger.Module
import dagger.Provides
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.caching.key.GitHubUserKey
import my.app.githubapp.mvp.model.caching.key.UsersRepositoriesKey
import my.app.githubapp.mvp.model.caching.networkDataSource.GitHubUserNetworkDataSource
import my.app.githubapp.mvp.model.caching.networkDataSource.UsersRepositoriesNetworkDataSource

@Module
interface UserGitHubSourceModule {

    companion object {
        @Provides
        @PerFragment
        fun providesUserGitHubDataSource(networkSource: GitHubUserNetworkDataSource): DataSource<GitHubUserKey, GitHubUser> {
            return DataSource.Builder<GitHubUserKey, GitHubUser>()
                .setNetworkDataSource(networkSource)
                .build()
        }

        @Provides
        @PerFragment
        fun providesUsersRepositoriesDataSource(networkSource: UsersRepositoriesNetworkDataSource): DataSource<UsersRepositoriesKey, List<GitHubRepo>> {
            return DataSource.Builder<UsersRepositoriesKey, List<GitHubRepo>>()
                .setNetworkDataSource(networkSource)
                .build()
        }
    }
}