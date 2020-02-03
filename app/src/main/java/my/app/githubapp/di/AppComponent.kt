package my.app.githubapp.di

import dagger.BindsInstance
import dagger.Component
import my.app.githubapp.di.RepositoryDetailsSubcomponent.RepositoryDetailSubcomponent
import my.app.githubapp.di.RepositoryDetailsSubcomponent.RepositoryDetailsSubcomponentFactory
import my.app.githubapp.di.RepositorySearchSubcomponent.RepositorySearchSubcomponent
import my.app.githubapp.di.RepositorySearchSubcomponent.RepositorySearchSubomponentFactory
import javax.inject.Singleton

@Singleton
@Component
    (modules = [AppComponentModule::class,
    RepositorySearchSubomponentFactory::class,
RepositoryDetailsSubcomponentFactory::class])

interface AppComponent {

    fun getRepositorySearchSubcomponentFactory() : RepositorySearchSubcomponent.Factory
    fun getRepositoryDetailsSubcmponentFactory()  : RepositoryDetailSubcomponent.Factory

    @Component.Factory
    interface Factory{
        fun create() : AppComponent
    }

}