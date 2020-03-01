package my.app.githubapp.di.repositorySearchSubcomponent

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.utils.sorter.SorterInterface
import my.app.githubapp.utils.sorter.gitHubRepoSorter.GitHubRepoSorter
import my.app.githubapp.utils.sorter.gitHubRepoSorter.GitHubRepoSorts
import my.app.githubapp.utils.sorter.gitHubRepoSorter.GitHubRepoSortsInterface

@Module
interface GitHubRepoSorterModule {

    @Binds
    @PerFragment
    fun bindsGitHubRepoSorter(sorter: GitHubRepoSorter): SorterInterface<GitHubRepo>

    companion object {

        @Provides
        @PerFragment
        fun bindsGitHubRepoSorts(): GitHubRepoSortsInterface {
            return GitHubRepoSorts
        }
    }
}
