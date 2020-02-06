package my.app.githubapp.di.userDetailsSubcompnent

import dagger.Subcomponent
import my.app.githubapp.di.dataSourceModule.RepositoryGitHubSourceModule
import my.app.githubapp.di.dataSourceModule.UserGitHubSourceModule
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.ui.userDetailsView.UserDetailsFragment

@PerFragment
@Subcomponent(
    modules = [
        UserDetailsModule::class,
        UserGitHubSourceModule::class,
        RepositoryGitHubSourceModule::class
    ]
)
interface UserDetailsSubcomponent {
    fun inject(userDetailsFragment: UserDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create() : UserDetailsSubcomponent
    }
}