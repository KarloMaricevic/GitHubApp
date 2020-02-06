package my.app.githubapp.di.repositorySearchSubcomponent

import dagger.Subcomponent
import my.app.githubapp.di.dataSourceModule.QueryGitHubSourceModule
import my.app.githubapp.di.dataSourceModule.RepositoryGitHubSourceModule
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.ui.repositorySearchView.RepositorySearchFragment

@PerFragment
@Subcomponent
    (modules = [RepositorySearchModule::class,
                QueryGitHubSourceModule::class,
                RepositoryGitHubSourceModule::class])
interface RepositorySearchSubcomponent {

    fun inject(repositorySearchFragment: RepositorySearchFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create() : RepositorySearchSubcomponent
    }
}