package my.app.githubapp.di.RepositoryDetailsSubcomponent

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.mvp.contract.RepositoryScreenContract.RepositoryDetailsInteractorInterface
import my.app.githubapp.mvp.contract.RepositoryScreenContract.RepositoryDetailsPresenterInterface
import my.app.githubapp.mvp.model.repositoryDetails.RepositoryDetailsGitHubService
import my.app.githubapp.mvp.model.repositoryDetails.RepositoryDetailsInteractor
import my.app.githubapp.mvp.presenter.RepositoryDetailPresenter
import retrofit2.Retrofit

@Module
interface RepositoryDetailsModule {

    @Binds
    @PerFragment
    fun providesRepositoryDetailsPresenter(presenter : RepositoryDetailPresenter) : RepositoryDetailsPresenterInterface

    @Binds
    @PerFragment
    fun providesRepositoryScreenInteractor(interactor : RepositoryDetailsInteractor) : RepositoryDetailsInteractorInterface

    companion object{

        @Provides
        @PerFragment
        fun providesRepositoryDetailsGitHubService(retrofit: Retrofit) : RepositoryDetailsGitHubService {

            return  retrofit
                .create(RepositoryDetailsGitHubService::class.java)
        }
    }

}