package my.app.githubapp.di.RepositorySearchSubcomponent

import dagger.Subcomponent
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.ui.RepositorySearchView.RepositorySearchFragment

@PerFragment
@Subcomponent
    (modules = [RepositorySearchModule::class])
interface RepositorySearchSubcomponent {

    fun inject(repositorySearchFragment: RepositorySearchFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create() : RepositorySearchSubcomponent
    }
}