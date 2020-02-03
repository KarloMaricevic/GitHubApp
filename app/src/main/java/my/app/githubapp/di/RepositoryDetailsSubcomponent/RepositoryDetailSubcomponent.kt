package my.app.githubapp.di.RepositoryDetailsSubcomponent

import dagger.BindsInstance
import dagger.BindsOptionalOf
import dagger.Subcomponent
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.ui.RepositoryDetailsView.RepositoryDetailsFragment
import javax.inject.Named


@PerFragment
@Subcomponent
    (modules = [RepositoryDetailsModule::class])
interface RepositoryDetailSubcomponent {

    fun inject(repositoryDetailsFragment: RepositoryDetailsFragment)


    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance @Named("ownerName") ownerName : String,@BindsInstance @Named("repoName") repoName : String ) : RepositoryDetailSubcomponent
    }
}