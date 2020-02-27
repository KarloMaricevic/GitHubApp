package my.app.githubapp.utils.sorter.GitHubRepoSorter

import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.ui.repositorySearchView.SORT_BY_DATE
import my.app.githubapp.ui.repositorySearchView.SORT_BY_FORKED
import my.app.githubapp.ui.repositorySearchView.SORT_BY_REPO_NAME
import my.app.githubapp.ui.repositorySearchView.SORT_BY_STAR
import my.app.githubapp.utils.sorter.SorterInterface
import javax.inject.Inject



class GitHubRepoSorter  @Inject constructor(private val mSorts : GitHubRepoSortsInterface) :
    SorterInterface<GitHubRepo> {
    override fun sortIterable(iterableToSort: Iterable<GitHubRepo>, sortType: Int): Iterable<GitHubRepo> {
        return when (sortType) {
            SORT_BY_REPO_NAME -> mSorts.sortByRepoName(iterableToSort)
            SORT_BY_STAR -> mSorts.sortByStar(iterableToSort)
            SORT_BY_DATE -> mSorts.sortByDate(iterableToSort)
            SORT_BY_FORKED -> mSorts.sortByForked(iterableToSort)
            else -> throw NoSuchMethodException("Sort not implemented")
        }
    }



}