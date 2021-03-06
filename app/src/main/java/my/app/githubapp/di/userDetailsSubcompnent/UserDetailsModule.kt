package my.app.githubapp.di.userDetailsSubcompnent

import dagger.Binds
import dagger.Module
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.mvp.contract.UserDetailsContract.UserDetailsInteractorInterface
import my.app.githubapp.mvp.contract.UserDetailsContract.UserDetailsPresenterAbstraction
import my.app.githubapp.mvp.contract.UserDetailsContract.UserDetailsRepositoryInterface
import my.app.githubapp.mvp.model.userDetails.UserDetailsInteractor
import my.app.githubapp.mvp.model.userDetails.UserDetailsRepository
import my.app.githubapp.mvp.presenter.UserDetailsPresenter

@Module
interface UserDetailsModule {

    @Binds
    @PerFragment
    fun providePresenter(userDetailsPresenter: UserDetailsPresenter): UserDetailsPresenterAbstraction

    @Binds
    @PerFragment
    fun provideInteractor(userDetailsInteractor: UserDetailsInteractor): UserDetailsInteractorInterface

    @Binds
    @PerFragment
    fun provideRepository(userDetailsRepository: UserDetailsRepository): UserDetailsRepositoryInterface
}
