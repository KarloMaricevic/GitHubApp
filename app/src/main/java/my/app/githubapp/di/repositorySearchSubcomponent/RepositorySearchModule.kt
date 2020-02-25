package my.app.githubapp.di.repositorySearchSubcomponent

import dagger.Binds
import dagger.Module
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.mvp.contract.RepositorySearchContract.*
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchInteractor
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchRepository
import my.app.githubapp.mvp.presenter.RepositorySearchPresenter


@Module
interface RepositorySearchModule {

    @Binds
    @PerFragment
    fun providesPresenter(presenter : RepositorySearchPresenter) : RepositorySearchPresenterAbstraction

    @Binds
    @PerFragment
    fun providesInteractor(interactor : RepositorySearchInteractor) : RepositorySearchInteractorInterface

    @Binds
    @PerFragment
    fun providesRepository(repository : RepositorySearchRepository) : RepositorySearchRepositoryInterface
}