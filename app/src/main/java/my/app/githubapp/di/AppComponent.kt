package my.app.githubapp.di

import dagger.BindsInstance
import dagger.Component
import my.app.githubapp.di.RepositorySearchSubcomponent.RepositorySearchSubcomponent
import my.app.githubapp.di.RepositorySearchSubcomponent.RepositorySearchSubomponentFactory
import javax.inject.Singleton

@Singleton
@Component
    (modules = [RepositorySearchSubomponentFactory::class,AppComponentModule::class])

interface AppComponent {

    fun getRepositorySearchSubcomponentFactory() : RepositorySearchSubcomponent.Factory


    @Component.Factory
    interface Factory{
        fun create() : AppComponent
    }

}