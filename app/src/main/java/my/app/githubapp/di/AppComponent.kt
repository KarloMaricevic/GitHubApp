package my.app.githubapp.di

import dagger.Component
import my.app.githubapp.di.repositoryDetailsSubcomponent.RepositoryDetailSubcomponent
import my.app.githubapp.di.repositoryDetailsSubcomponent.RepositoryDetailsSubcomponentFactory
import my.app.githubapp.di.repositorySearchSubcomponent.RepositorySearchSubcomponent
import my.app.githubapp.di.repositorySearchSubcomponent.RepositorySearchSubomponentFactory
import my.app.githubapp.di.userDetailsSubcompnent.UserDetailSubcomponentFactory
import my.app.githubapp.di.userDetailsSubcompnent.UserDetailsSubcomponent
import javax.inject.Singleton

@Singleton
@Component
    (
    modules = [
        RetrofitServicesModule::class,
        SchedulerModule::class,
        RepositorySearchSubomponentFactory::class,
        RepositoryDetailsSubcomponentFactory::class,
        MapperModule::class,
        UserDetailSubcomponentFactory::class
    ]
)

interface AppComponent {

    fun getRepositorySearchSubcomponentFactory(): RepositorySearchSubcomponent.Factory
    fun getRepositoryDetailsSubcmponentFactory(): RepositoryDetailSubcomponent.Factory
    fun getUserDetailsSubcomponentFactory(): UserDetailsSubcomponent.Factory

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}
