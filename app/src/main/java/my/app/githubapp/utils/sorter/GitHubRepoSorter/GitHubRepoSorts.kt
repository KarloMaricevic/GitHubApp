package my.app.githubapp.utils.sorter.GitHubRepoSorter

import my.app.githubapp.domain.GitHubRepo


object GitHubRepoSorts :
    GitHubRepoSortsInterface  {
    override fun sortByRepoName(iterable: Iterable<GitHubRepo>) : Iterable<GitHubRepo> {
        return iterable.sortedBy { it.name }
    }

    override fun sortByStar(iterable: Iterable<GitHubRepo>) : Iterable<GitHubRepo> {
        return iterable.sortedBy { it.staredNumber }
    }

    override fun sortByDate(iterable: Iterable<GitHubRepo>) : Iterable<GitHubRepo> {
        return iterable.sortedBy { it.createdAt }
    }

    override fun sortByForked(iterable: Iterable<GitHubRepo>) : Iterable<GitHubRepo> {
        return iterable.sortedBy { it.forks }
    }


}