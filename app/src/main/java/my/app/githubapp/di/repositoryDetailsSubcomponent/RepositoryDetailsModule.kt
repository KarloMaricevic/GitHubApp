package my.app.githubapp.di.repositoryDetailsSubcomponent

import dagger.Binds
import dagger.Module
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.*
import my.app.githubapp.mvp.model.repositoryDetails.*
import my.app.githubapp.mvp.presenter.RepositoryDetailPresenter

@Module
interface RepositoryDetailsModule {

    @Binds
    @PerFragment
    fun providesPresenter(presenter : RepositoryDetailPresenter) : RepositoryDetailsPresenterInterface

    @Binds
    @PerFragment
    fun providesInteractor(interactor : RepositoryDetailsInteractor) : RepositoryDetailsInteractorInterface

    @Binds
    @PerFragment
    fun providesRepository(repository : RepositoryDetailsRepository) : RepositoryDetailsRepositoryInterface
}