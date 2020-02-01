package my.app.githubapp.di.RepositorySearchSubcomponent

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchGitHubService
import my.app.githubapp.mvp.presenter.RepositorySearchPresenter
import retrofit2.Retrofit


@Module
interface RepositorySearchModule {

    @Binds
    @PerFragment
    fun providesRepositorySearchPresenter(presenter : RepositorySearchPresenter) : RepositorySearchContract.RepositorySearchPresenterInterface

    companion object{

        @Provides
        @PerFragment
        fun providesRepositorySearchGitHubService(retrofit: Retrofit) : RepositorySearchGitHubService {
            return retrofit.create(RepositorySearchGitHubService::class.java)
        }
    }
}