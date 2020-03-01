package my.app.githubapp.di.repositoryDetailsSubcomponent

import dagger.Subcomponent
import my.app.githubapp.di.dataSourceModule.RepositoryGitHubSourceModule
import my.app.githubapp.di.dataSourceModule.UserGitHubSourceModule
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.ui.repositoryDetailsView.RepositoryDetailsFragment

@PerFragment
@Subcomponent
    (
    modules = [RepositoryDetailsModule::class,
        RepositoryGitHubSourceModule::class,
        UserGitHubSourceModule::class]
)
interface RepositoryDetailSubcomponent {

    fun inject(repositoryDetailsFragment: RepositoryDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): RepositoryDetailSubcomponent
    }
}
